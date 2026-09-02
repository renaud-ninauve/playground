package fr.ninauve.renaud.kata.maisoneclat.privileges;

import java.math.BigDecimal;
import java.util.List;

public class ForIconClient implements PrivilegeComputer {
  @Override
  public Privilege forClientTiers(ClientTier clientTier, BigDecimal totalWithoutTax) {
    var privilege = totalWithoutTax.multiply(new BigDecimal("0.15"));
    return new Privilege(
        privilege.negate(), "Icon client privilege", List.of("Dedicated artisan follow-up"));
  }

  @Override
  public ClientTier appliesTo() {
    return ClientTier.ICON;
  }
}
