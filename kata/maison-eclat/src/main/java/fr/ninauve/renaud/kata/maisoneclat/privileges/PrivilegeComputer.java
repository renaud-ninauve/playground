package fr.ninauve.renaud.kata.maisoneclat.privileges;

import java.math.BigDecimal;

public interface PrivilegeComputer {
  Privilege forClientTiers(ClientTier clientTier, BigDecimal totalWithoutTax);

  ClientTier appliesTo();
}
