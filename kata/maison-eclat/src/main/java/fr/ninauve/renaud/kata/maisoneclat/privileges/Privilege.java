package fr.ninauve.renaud.kata.maisoneclat.privileges;

import java.math.BigDecimal;
import java.util.List;

public record Privilege(BigDecimal discount, String description, List<String> notes) {
    public BigDecimal appliesDiscount(BigDecimal total) {
        return total.add(discount);
    }
}
