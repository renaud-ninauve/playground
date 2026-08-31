package fr.ninauve.renaud.kata.maisoneclat;

import static fr.ninauve.renaud.kata.maisoneclat.ClientTier.*;
import static fr.ninauve.renaud.kata.maisoneclat.DestinationCountry.*;
import static fr.ninauve.renaud.kata.maisoneclat.ProductType.HANDBAG;
import static fr.ninauve.renaud.kata.maisoneclat.ProductType.WATCH;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

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
    return prepareQuote(
        clientName,
        ClientTier.valueOf(clientTier),
        ProductType.valueOf(productType),
        productName,
        basePrice,
        giftWrapping,
        engraving,
        privateDelivery,
        DestinationCountry.valueOf(destinationCountry));
  }

  public Quote prepareQuote(
      String clientName,
      ClientTier clientTier,
      ProductType productType,
      String productName,
      BigDecimal basePrice,
      boolean giftWrapping,
      boolean engraving,
      boolean privateDelivery,
      DestinationCountry destinationCountry) {

    var lineItems = new ArrayList<QuoteLine>();
    var total = basePrice;
    var notes = new ArrayList<String>();

    // Product-specific handling
    switch (productType) {
      case HANDBAG -> notes.add("Hand-finished leather inspection included");
      case WATCH -> {
        total = total.add(new BigDecimal("350.00"));
        notes.add("Mechanical calibration included");
      }
      case FRAGRANCE -> {
        total = total.add(new BigDecimal("25.00"));
        notes.add("Personal fragrance consultation included");
      }
    }

    lineItems.add(new QuoteLine(productName, basePrice));

    // Optional services
    if (giftWrapping) {
      var wrappingCost =
          WATCH.equals(productType) ? new BigDecimal("45.00") : new BigDecimal("25.00");

      total = total.add(wrappingCost);
      lineItems.add(new QuoteLine("Signature gift wrapping", wrappingCost));
    }

    if (engraving) {
      if (!WATCH.equals(productType) && !HANDBAG.equals(productType)) {
        throw new IllegalArgumentException("Engraving is available only for watches and handbags");
      }

      var engravingCost = new BigDecimal("80.00");
      total = total.add(engravingCost);
      lineItems.add(new QuoteLine("Personal engraving", engravingCost));
    }

    if (privateDelivery) {
      var deliveryCost =
          switch (destinationCountry) {
            case FRANCE -> new BigDecimal("60.00");
            case ITALY -> new BigDecimal("90.00");
            case JAPAN, USA, UAE -> new BigDecimal("220.00");
            default -> throw new IllegalArgumentException("Unsupported destination");
          };

      total = total.add(deliveryCost);
      lineItems.add(new QuoteLine("White-glove private delivery", deliveryCost));
    }

    // Privileges by client tier
    switch (clientTier) {
      case ICON -> {
        var privilege = total.multiply(new BigDecimal("0.15"));
        total = total.subtract(privilege);
        lineItems.add(new QuoteLine("Icon client privilege", privilege.negate()));
        notes.add("Dedicated artisan follow-up");
      }
      case PRIVILEGE -> {
        var privilege = total.multiply(new BigDecimal("0.08"));
        total = total.subtract(privilege);
        lineItems.add(new QuoteLine("Privilège client privilege", privilege.negate()));
      }
      default -> {
        // no privilege
      }
    }

    // Regional tax
    if (FRANCE.equals(destinationCountry) || ITALY.equals(destinationCountry)) {
      var tax = total.multiply(new BigDecimal("0.20"));
      total = total.add(tax);
      lineItems.add(new QuoteLine("VAT", tax));
    } else if (JAPAN.equals(destinationCountry)) {
      var tax = total.multiply(new BigDecimal("0.10"));
      total = total.add(tax);
      lineItems.add(new QuoteLine("Consumption tax", tax));
    }

    total = total.setScale(2, RoundingMode.HALF_UP);

    var reference =
        "ME-"
            + productType.name().substring(0, 2)
            + "-"
            + clientName.replaceAll("\\s+", "").toUpperCase()
            + "-"
            + System.currentTimeMillis();

    return new Quote(reference, clientName, lineItems, total, notes);
  }

  public record QuoteLine(String description, BigDecimal amount) {}

  @Builder
  public record Quote(
      String reference,
      String clientName,
      List<QuoteLine> lineItems,
      BigDecimal total,
      List<String> notes) {}
}
