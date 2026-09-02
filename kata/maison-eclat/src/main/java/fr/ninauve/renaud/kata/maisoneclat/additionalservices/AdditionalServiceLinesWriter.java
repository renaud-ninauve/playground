package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.QuotePart;
import fr.ninauve.renaud.kata.maisoneclat.QuoteRequest;

public interface AdditionalServiceLinesWriter {
    QuotePart forRequest(QuoteRequest request);
    AdditionalService appliesToService();
}