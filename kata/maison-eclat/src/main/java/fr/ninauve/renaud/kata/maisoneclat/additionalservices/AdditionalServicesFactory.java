package fr.ninauve.renaud.kata.maisoneclat.additionalservices;

import fr.ninauve.renaud.kata.maisoneclat.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdditionalServicesFactory {
  private final Map<AdditionalServiceType, AdditionalServiceFactory> writers;

  {
    writers =
        Stream.of(new GiftWrappingServiceFactory(), new EngravingServiceFactory(), new PrivateDeliveryServiceFactory())
            .collect(
                Collectors.toMap(
                    AdditionalServiceFactory::appliesToService, Function.identity()));
  }

  public List<AdditionalService> forRequest(QuoteRequest request) {
    return request.additionalServiceTypes().stream()
        .map(s -> writers.get(s).forRequest(request))
        .toList();
  }
}