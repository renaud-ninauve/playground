package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;

import java.util.List;

public class GiftWrappingWriter implements AdditionalServiceLinesWriter {
    @Override
    public QuotePart forRequest(QuoteRequest request) {
        LuxuryProduct luxuryProduct = request.luxuryProduct();
        var wrappingCost = luxuryProduct.wrappingCost();
        return new QuotePart(
                wrappingCost,
                List.of(new QuoteLine("Signature gift wrapping", wrappingCost)),
                List.of());
    }

    @Override
    public AdditionalService appliesToService() {
        return AdditionalService.GIFT_WRAPPING;
    }
}