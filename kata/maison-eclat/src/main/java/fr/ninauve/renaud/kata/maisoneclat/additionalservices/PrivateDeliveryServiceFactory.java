package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;
import java.math.BigDecimal;

public class PrivateDeliveryServiceFactory implements AdditionalServiceFactory {
  @Override
  public AdditionalService forRequest(QuoteRequest request) {
    DestinationCountry destinationCountry = request.destinationCountry();
    var deliveryCost =
        switch (destinationCountry) {
          case FRANCE -> new BigDecimal("60.00");
          case ITALY -> new BigDecimal("90.00");
          case JAPAN, USA, UAE -> new BigDecimal("220.00");
          default -> throw new IllegalArgumentException("Unsupported destination");
        };
    return new AdditionalService(deliveryCost, "White-glove private delivery");
  }

  @Override
  public AdditionalServiceType appliesToService() {
    return AdditionalServiceType.PRIVATE_DELIVERY;
  }
}
