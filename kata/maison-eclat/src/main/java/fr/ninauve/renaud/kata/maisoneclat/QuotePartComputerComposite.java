package fr.ninauve.renaud.kata.maisoneclat;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuotePartComputerComposite implements QuotePartComputer {
  private final List<QuotePartComputer> computers;

  @Override
  public QuotePart compute(QuoteRequest request) {
    return computers.stream()
        .map(computer -> computer.compute(request))
        .reduce(QuotePart.EMPTY, QuotePart::append);
  }
}
