package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;

import java.math.BigDecimal;
import java.util.List;

public class EngravingWriter implements AdditionalServiceLinesWriter {
    @Override
    public QuotePart forRequest(QuoteRequest request) {
        LuxuryProduct luxuryProduct = request.luxuryProduct();
        if (!luxuryProduct.engravingSupported()) {
            throw new IllegalArgumentException("Engraving is available only for watches and handbags");
        }

        var engravingCost = new BigDecimal("80.00");
        return new QuotePart(
                engravingCost,
                List.of(new QuoteLine("Personal engraving", engravingCost)),
                List.of());
    }

    @Override
    public AdditionalService appliesToService() {
        return AdditionalService.ENGRAVING;
    }
}