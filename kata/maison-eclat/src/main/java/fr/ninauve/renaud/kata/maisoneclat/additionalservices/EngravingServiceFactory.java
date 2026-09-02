package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;
import java.math.BigDecimal;

public class EngravingServiceFactory implements AdditionalServiceFactory {
  @Override
  public AdditionalService forRequest(QuoteRequest request) {
    LuxuryProduct luxuryProduct = request.luxuryProduct();
    if (!luxuryProduct.engravingSupported()) {
      throw new IllegalArgumentException("Engraving is available only for watches and handbags");
    }

    var engravingCost = new BigDecimal("80.00");
    return new AdditionalService(engravingCost, "Personal engraving");
  }

  @Override
  public AdditionalServiceType appliesToService() {
    return AdditionalServiceType.ENGRAVING;
  }
}
