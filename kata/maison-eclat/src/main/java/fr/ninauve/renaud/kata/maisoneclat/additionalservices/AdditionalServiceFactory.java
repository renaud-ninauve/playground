package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.QuoteRequest;

public interface AdditionalServiceFactory {
  AdditionalService forRequest(QuoteRequest request);

  AdditionalServiceType appliesToService();
}
