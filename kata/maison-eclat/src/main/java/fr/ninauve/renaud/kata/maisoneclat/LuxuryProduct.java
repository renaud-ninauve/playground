package fr.ninauve.renaud.kata.maisoneclat;

import java.math.BigDecimal;

public record LuxuryProduct(String includedNote,
                            BigDecimal additionalCost,
                            BigDecimal wrappingCost,
                            boolean engravingSupported) {

    static LuxuryProduct ofType(ProductType productType) {
        return switch (productType) {
            case HANDBAG -> new LuxuryProduct(
                    "Hand-finished leather inspection included",
                    BigDecimal.ZERO,
                    new BigDecimal("25.00"),
                    true);
            case FRAGRANCE -> new LuxuryProduct(
                    "Personal fragrance consultation included",
                    new BigDecimal("25.00"),
                    new BigDecimal("25.00"),
                    false);
            case WATCH -> new LuxuryProduct(
                    "Mechanical calibration included",
                    new BigDecimal("350.00"),
                    new BigDecimal("45.00"),
                    true);
        };
    }
}
