package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllServicesWriter {
  private final Map<AdditionalService, AdditionalServiceLinesWriter> writers;

  {
    writers =
        Stream.of(new GiftWrappingWriter(), new EngravingWriter(), new PrivateDeliveryWriter())
            .collect(
                Collectors.toMap(
                    AdditionalServiceLinesWriter::appliesToService, Function.identity()));
  }

  public QuotePart forRequest(QuoteRequest request) {
    return request.additionalServices().stream()
        .map(s -> writers.get(s).forRequest(request))
        .reduce(QuotePart.EMPTY, QuotePart::append);
  }
}
