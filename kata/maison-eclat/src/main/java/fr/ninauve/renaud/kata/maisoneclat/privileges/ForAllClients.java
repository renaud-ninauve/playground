package fr.ninauve.renaud.kata.maisoneclat.privileges;

import fr.ninauve.renaud.kata.maisoneclat.additionalservices.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForAllClients {
  private final Map<ClientTier, PrivilegeComputer> writers;
  {
    writers =
        Stream.of(new ForPrivilegeClient(), new ForIconClient())
            .collect(
                Collectors.toMap(
                        PrivilegeComputer::appliesTo, Function.identity()));
  }

  public Optional<Privilege> forClientTiers(ClientTier clientTier, BigDecimal totalWithoutTax) {
    PrivilegeComputer privilegeComputer = writers.get(clientTier);
    return Optional.ofNullable(privilegeComputer)
            .map(computer -> computer.forClientTiers(clientTier, totalWithoutTax));
  }
}