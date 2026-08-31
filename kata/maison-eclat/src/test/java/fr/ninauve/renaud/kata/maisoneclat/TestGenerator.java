package fr.ninauve.renaud.kata.maisoneclat;

import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class TestGenerator {
  static final Path SCENARIOS_FILE = Path.of("src/test/resources/scenarios.json");
  private static final List<String> CLIENT_NAME = List.of("John Connor", "", " ");
  private static final List<String> CLIENT_TIER = List.of("ICON", "PRIVILEGE", "GUEST", "XXXX");
  private static final List<String> PRODUCT_TYPE = List.of("HANDBAG", "FRAGRANCE", "WATCH", "XXXX");
  private static final List<String> PRODUCT_NAME = List.of("TipTop Product", "");
  private static final List<BigDecimal> BASE_PRICE =
      List.of(new BigDecimal("1000"), new BigDecimal("-1000"), BigDecimal.ZERO);
  private static final List<Boolean> GIFT_WRAPPING = List.of(true, false);
  private static final List<Boolean> ENGRAVING = List.of(true, false);
  private static final List<Boolean> PRIVATE_DELIVERY = List.of(true, false);
  private static final List<String> DESTINATION_COUNTRY =
      List.of("FRANCE", "ITALY", "JAPAN", "USA", "UAE", "XXXX");

  static void main() throws IOException {
    List<Scenario> scenarioList =
        CLIENT_NAME.stream()
            .map(clientName -> PrepareQuoteArgs.builder().clientName(clientName).build())
            .flatMap(
                args ->
                    CLIENT_TIER.stream()
                        .map(clientTier -> args.toBuilder().clientTier(clientTier).build()))
            .flatMap(
                args ->
                    PRODUCT_TYPE.stream()
                        .map(productType -> args.toBuilder().productType(productType).build()))
            .flatMap(
                args ->
                    PRODUCT_NAME.stream()
                        .map(productName -> args.toBuilder().productName(productName).build()))
            .flatMap(
                args ->
                    BASE_PRICE.stream()
                        .map(basePrice -> args.toBuilder().basePrice(basePrice).build()))
            .flatMap(
                args ->
                    GIFT_WRAPPING.stream()
                        .map(giftWrapping -> args.toBuilder().giftWrapping(giftWrapping).build()))
            .flatMap(
                args ->
                    ENGRAVING.stream()
                        .map(engraving -> args.toBuilder().engraving(engraving).build()))
            .flatMap(
                args ->
                    PRIVATE_DELIVERY.stream()
                        .map(
                            privateDelivery ->
                                args.toBuilder().privateDelivery(privateDelivery).build()))
            .flatMap(
                args ->
                    DESTINATION_COUNTRY.stream()
                        .map(
                            destinationCountry ->
                                args.toBuilder().destinationCountry(destinationCountry).build()))
            .map(TestGenerator::generateScenario)
            .toList();

      String scenariosJson = new JsonMapper().writeValueAsString(scenarioList);
      Files.createDirectories(SCENARIOS_FILE.getParent());
      Files.writeString(SCENARIOS_FILE, scenariosJson);
  }

  static Scenario generateScenario(PrepareQuoteArgs args) {
    ConciergeService conciergeService = new ConciergeService();
    try {
      ConciergeService.Quote quote =
          conciergeService.prepareQuote(
              args.clientName(),
              args.clientTier(),
              args.productType(),
              args.productName(),
              args.basePrice(),
              args.engraving(),
              args.giftWrapping(),
              args.privateDelivery(),
              args.destinationCountry());
      return new Scenario(args, new Expected(ExpectedResult.SUCCESS, quote));
    } catch (Exception e) {
      return new Scenario(args, new Expected(ExpectedResult.FAILED, null));
    }
  }
}
