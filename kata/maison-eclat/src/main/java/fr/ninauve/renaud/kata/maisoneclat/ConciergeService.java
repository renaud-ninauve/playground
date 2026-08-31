package fr.ninauve.renaud.kata.maisoneclat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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
            String destinationCountry
    ) {
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

        var lineItems = new ArrayList<QuoteLine>();
        var total = basePrice;
        var notes = new ArrayList<String>();

        // Product-specific handling
        if (productType.equals("HANDBAG")) {
            notes.add("Hand-finished leather inspection included");
        } else if (productType.equals("WATCH")) {
            total = total.add(new BigDecimal("350.00"));
            notes.add("Mechanical calibration included");
        } else if (productType.equals("FRAGRANCE")) {
            total = total.add(new BigDecimal("25.00"));
            notes.add("Personal fragrance consultation included");
        }

        lineItems.add(new QuoteLine(productName, basePrice));

        // Optional services
        if (giftWrapping) {
            var wrappingCost = productType.equals("WATCH")
                    ? new BigDecimal("45.00")
                    : new BigDecimal("25.00");

            total = total.add(wrappingCost);
            lineItems.add(new QuoteLine("Signature gift wrapping", wrappingCost));
        }

        if (engraving) {
            if (!productType.equals("WATCH") && !productType.equals("HANDBAG")) {
                throw new IllegalArgumentException(
                        "Engraving is available only for watches and handbags"
                );
            }

            var engravingCost = new BigDecimal("80.00");
            total = total.add(engravingCost);
            lineItems.add(new QuoteLine("Personal engraving", engravingCost));
        }

        if (privateDelivery) {
            var deliveryCost = switch (destinationCountry) {
                case "FRANCE" -> new BigDecimal("60.00");
                case "ITALY" -> new BigDecimal("90.00");
                case "JAPAN", "USA", "UAE" -> new BigDecimal("220.00");
                default -> throw new IllegalArgumentException("Unsupported destination");
            };

            total = total.add(deliveryCost);
            lineItems.add(new QuoteLine("White-glove private delivery", deliveryCost));
        }

        // Privileges by client tier
        if (clientTier.equals("ICON")) {
            var privilege = total.multiply(new BigDecimal("0.15"));
            total = total.subtract(privilege);
            lineItems.add(new QuoteLine("Icon client privilege", privilege.negate()));
            notes.add("Dedicated artisan follow-up");
        } else if (clientTier.equals("PRIVILEGE")) {
            var privilege = total.multiply(new BigDecimal("0.08"));
            total = total.subtract(privilege);
            lineItems.add(new QuoteLine("Privilège client privilege", privilege.negate()));
        } else if (clientTier.equals("GUEST")) {
            // No reduction
        } else {
            throw new IllegalArgumentException("Unknown client tier: " + clientTier);
        }

        // Regional tax
        if (destinationCountry.equals("FRANCE") || destinationCountry.equals("ITALY")) {
            var tax = total.multiply(new BigDecimal("0.20"));
            total = total.add(tax);
            lineItems.add(new QuoteLine("VAT", tax));
        } else if (destinationCountry.equals("JAPAN")) {
            var tax = total.multiply(new BigDecimal("0.10"));
            total = total.add(tax);
            lineItems.add(new QuoteLine("Consumption tax", tax));
        }

        total = total.setScale(2, RoundingMode.HALF_UP);

        var reference = "ME-" + productType.substring(0, 2)
                + "-" + clientName.replaceAll("\\s+", "").toUpperCase()
                + "-" + System.currentTimeMillis();

        return new Quote(reference, clientName, lineItems, total, notes);
    }

    public record QuoteLine(String description, BigDecimal amount) { }

    public record Quote(
            String reference,
            String clientName,
            List<QuoteLine> lineItems,
            BigDecimal total,
            List<String> notes
    ) { }
}
