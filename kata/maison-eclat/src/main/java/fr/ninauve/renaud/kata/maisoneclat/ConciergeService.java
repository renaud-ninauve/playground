package fr.ninauve.renaud.kata.maisoneclat;

import fr.ninauve.renaud.kata.maisoneclat.additionalservices.*;
import fr.ninauve.renaud.kata.maisoneclat.privileges.ClientTier;
import fr.ninauve.renaud.kata.maisoneclat.privileges.ForAllClients;
import fr.ninauve.renaud.kata.maisoneclat.privileges.Privilege;

import static fr.ninauve.renaud.kata.maisoneclat.DestinationCountry.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ConciergeService {

  public Quote prepareQuote(
      String clientName,
      String clientTier,
      String productType,
      String productName,
      BigDecimal basePrice,
      boolean giftWrapping,
      boolean engraving,
      boolean privateDelivery,
      String destinationCountry) {
    if (clientName == null || clientName.isBlank()) {
      throw new IllegalArgumentException("A client name is required");
    }

    if (basePrice == null || basePrice.signum() <= 0) {
      throw new IllegalArgumentException("The product price must be positive");
    }

    if (!List.of("HANDBAG", "WATCH", "FRAGRANCE").contains(productType)) {
      throw new IllegalArgumentException("Unknown luxury product: " + productType);
    }

    if (!List.of("FRANCE", "ITALY", "JAPAN", "USA", "UAE").contains(destinationCountry)) {
      throw new IllegalArgumentException("Maison Éclat does not deliver to " + destinationCountry);
    }
    ProductType luxuryProductType = ProductType.valueOf(productType);
    LuxuryProduct luxuryProduct = LuxuryProduct.ofType(luxuryProductType);
    Set<AdditionalService> additionalServices = new TreeSet<>();
    if (giftWrapping) {
      additionalServices.add(AdditionalService.GIFT_WRAPPING);
    }
    if (engraving) {
      additionalServices.add(AdditionalService.ENGRAVING);
    }
    if (privateDelivery) {
      additionalServices.add(AdditionalService.PRIVATE_DELIVERY);
    }
    QuoteRequest request = new QuoteRequest(clientName,
            ClientTier.valueOf(clientTier),
            luxuryProduct,
            productName,
            basePrice,
            additionalServices,
            DestinationCountry.valueOf(destinationCountry));
    return prepareQuote(request);
  }

  public Quote prepareQuote(QuoteRequest request) {

    var lineItems = new ArrayList<QuoteLine>();
    var total = request.basePrice();
    var notes = new ArrayList<String>();

    total = total.add(request.luxuryProduct().additionalCost());
    notes.add(request.luxuryProduct().includedNote());

    lineItems.add(new QuoteLine(request.productName(), request.basePrice()));

    QuotePart quoteParts =
        new AllServicesWriter().forRequest(request);
    total = total.add(quoteParts.subTotal());
    lineItems.addAll(quoteParts.lineItems());
    notes.addAll(quoteParts.notes());

    Optional<Privilege> privilegeOpt = new ForAllClients().forClientTiers(request.clientTier(), total);
    if (privilegeOpt.isPresent()) {
      Privilege privilege = privilegeOpt.get();
      total = privilege.appliesDiscount(total);
      lineItems.add(new QuoteLine(privilege.description(), privilege.discount()));
      notes.addAll(privilege.notes());
    }

    // Regional tax
    if (FRANCE.equals(request.destinationCountry()) || ITALY.equals(request.destinationCountry())) {
      var tax = total.multiply(new BigDecimal("0.20"));
      total = total.add(tax);
      lineItems.add(new QuoteLine("VAT", tax));
    } else if (JAPAN.equals(request.destinationCountry())) {
      var tax = total.multiply(new BigDecimal("0.10"));
      total = total.add(tax);
      lineItems.add(new QuoteLine("Consumption tax", tax));
    }

    total = total.setScale(2, RoundingMode.HALF_UP);

    var reference =
        "ME-"
            + request.luxuryProduct().productType().name().substring(0, 2)
            + "-"
            + request.clientName().replaceAll("\\s+", "").toUpperCase()
            + "-"
            + System.currentTimeMillis();

    return new Quote(reference, request.clientName(), lineItems, total, notes);
  }

}
