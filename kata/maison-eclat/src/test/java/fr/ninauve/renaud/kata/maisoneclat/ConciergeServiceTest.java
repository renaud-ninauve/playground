package fr.ninauve.renaud.kata.maisoneclat;

import static fr.ninauve.renaud.kata.maisoneclat.TestGenerator.SCENARIOS_FILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.ninauve.renaud.kata.maisoneclat.ConciergeService.Quote;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

class ConciergeServiceTest {

  static Stream<Arguments> should_prepare_quote() {
    JsonMapper jsonMapper = JsonMapper.builder().build();
    List<Scenario> scenarios =
        jsonMapper.readValue(SCENARIOS_FILE, new TypeReference<List<Scenario>>() {});
    return scenarios.stream().map(Arguments::of);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource
  void should_prepare_quote(Scenario scenario) {
    PrepareQuoteArgs args = scenario.args();
    switch (scenario.expected().expectedResult()) {
      case SUCCESS -> should_succeed(args, scenario.expected().quote());
      case FAILED -> should_fail(args);
    }
  }

  void should_succeed(PrepareQuoteArgs args, Quote expected) {
    Quote actual = prepareQuote(args);

    assertThat(actual)
        .extracting("clientName", "total", "lineItems", "notes")
        .isEqualTo(
            List.of(
                expected.clientName(), expected.total(), expected.lineItems(), expected.notes()));
    int lastSeparator = expected.reference().lastIndexOf('-');
    assertThat(actual.reference()).startsWith(expected.reference().substring(0, lastSeparator + 1));
  }

  void should_fail(PrepareQuoteArgs args) {
    assertThrows(Exception.class, () -> prepareQuote(args));
  }

  private Quote prepareQuote(PrepareQuoteArgs args) {
    return new ConciergeService()
        .prepareQuote(
            args.clientName(),
            args.clientTier(),
            args.productType(),
            args.productName(),
            args.basePrice(),
            args.engraving(),
            args.giftWrapping(),
            args.privateDelivery(),
            args.destinationCountry());
  }
}
