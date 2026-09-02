package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;

import java.math.BigDecimal;
import java.util.List;

public class PrivateDeliveryWriter implements AdditionalServiceLinesWriter {
    @Override
    public QuotePart forRequest(QuoteRequest request) {
        DestinationCountry destinationCountry = request.destinationCountry();
        var deliveryCost =
                switch (destinationCountry) {
                    case FRANCE -> new BigDecimal("60.00");
                    case ITALY -> new BigDecimal("90.00");
                    case JAPAN, USA, UAE -> new BigDecimal("220.00");
                    default -> throw new IllegalArgumentException("Unsupported destination");
                };
        return new QuotePart(
                deliveryCost,
                List.of(new QuoteLine("White-glove private delivery", deliveryCost)),
                List.of());
    }

    @Override
    public AdditionalService appliesToService() {
        return AdditionalService.PRIVATE_DELIVERY;
    }
}