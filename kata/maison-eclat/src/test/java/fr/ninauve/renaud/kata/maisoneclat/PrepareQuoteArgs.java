package fr.ninauve.renaud.kata.maisoneclat;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
record PrepareQuoteArgs(
    String clientName,
    String clientTier,
    String productType,
    String productName,
    BigDecimal basePrice,
    boolean giftWrapping,
    boolean engraving,
    boolean privateDelivery,
    String destinationCountry) {}
