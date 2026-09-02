package fr.ninauve.renaud.kata.maisoneclat.privileges;

import java.math.BigDecimal;
import java.util.List;

public class ForPrivilegeClient implements PrivilegeComputer {
  @Override
  public Privilege forClientTiers(ClientTier clientTier, BigDecimal totalWithoutTax) {
    var privilege = totalWithoutTax.multiply(new BigDecimal("0.08"));
    BigDecimal total = totalWithoutTax.subtract(privilege);
    return new Privilege(privilege.negate(), "Privilège client privilege", List.of());
  }

  @Override
  public ClientTier appliesTo() {
    return ClientTier.PRIVILEGE;
  }
}