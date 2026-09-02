package fr.ninauve.renaud.kata.maisoneclat;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record Quote(
    String reference,
    String clientName,
    List<QuoteLine> lineItems,
    BigDecimal total,
    List<String> notes) {}
