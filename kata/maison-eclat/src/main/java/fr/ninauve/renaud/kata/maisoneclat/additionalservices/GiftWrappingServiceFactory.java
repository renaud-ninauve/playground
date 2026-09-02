package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;

public class GiftWrappingServiceFactory implements AdditionalServiceFactory {
    @Override
    public AdditionalService forRequest(QuoteRequest request) {
        LuxuryProduct luxuryProduct = request.luxuryProduct();
        var wrappingCost = luxuryProduct.wrappingCost();
        return new AdditionalService(
                wrappingCost,
                "Signature gift wrapping");
    }

    @Override
    public AdditionalServiceType appliesToService() {
        return AdditionalServiceType.GIFT_WRAPPING;
    }
}