package fr.ninauve.renaud.kata.maisoneclat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record QuotePart(BigDecimal subTotal, List<QuoteLine> lineItems, List<String> notes) {

  public static final QuotePart EMPTY = new QuotePart(BigDecimal.ZERO, List.of(), List.of());

  public QuotePart append(QuotePart other) {
    List<QuoteLine> resultLines = new ArrayList<>(lineItems);
    resultLines.addAll(other.lineItems());
    List<String> resultNotes = new ArrayList<>(notes);
    resultNotes.addAll(other.notes());
    BigDecimal resultTotal = subTotal.add(other.subTotal());
    return new QuotePart(resultTotal, resultLines, resultNotes);
  }
}
