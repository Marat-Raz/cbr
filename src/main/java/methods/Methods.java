package methods;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.restassured.path.json.JsonPath.from;

import com.codeborne.selenide.SelenideElement;
import dto.CurrencyRate;
import io.qameta.allure.Step;
import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class Methods {

  @Step("Возвращает элемент с href")
  public static SelenideElement getElementByHref(String href) {
    return $("[href='" + href + "']");
  }

  @Step("Жду, проверяю наличие элемента и что он виден на странице")
  public static void waitShouldBeVisible(SelenideElement selenideElement) {
    selenideElement.shouldBe(visible, Duration.ofSeconds(10));
  }

  @SneakyThrows
  @Step("Сохранить курсы в JSON файл: {filename}")
  public static void saveRatesToJson(List<CurrencyRate> rates, String filename) {
    File file = new File(filename);

    File parentDir = file.getParentFile();
    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs();
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
      writer.println("[");
      for (int i = 0; i < rates.size(); i++) {
        CurrencyRate rate = rates.get(i);
        writer.println("  {");
        writer.println("    \"currency_name\": \"" + rate.getCurrencyName() + "\",");
        writer.println("    \"currency_code\": \"" + rate.getCurrencyCode() + "\",");
        writer.println("    \"exchange_rate\": " + rate.getExchangeRate() + ",");
        writer.println("    \"unit\": " + rate.getUnit());
        writer.print("  }");
        if (i < rates.size() - 1) {
          writer.print(",");
        }
        writer.println();
      }
      writer.println("]");
    }
  }

  @SneakyThrows
  @Step("Загрузить курсы из JSON файла: {filename}")
  public static List<CurrencyRate> loadRatesFromJson(String filename) {
    File file = new File(filename);

    if (!file.exists()) {
      throw new FileNotFoundException("Файл не найден: " + file.getAbsolutePath());
    }

    String jsonContent = new String(java.nio.file.Files.readAllBytes(file.toPath()));

    return from(jsonContent).getList("", CurrencyRate.class);
  }

  @Step("Сравнить текущие курсы с эталонными")
  public static List<String> compareRates(List<CurrencyRate> currentRates, List<CurrencyRate> etalonRates) {
    List<String> differences = new ArrayList<>();

    Map<String, CurrencyRate> currentMap = currentRates.stream()
        .collect(Collectors.toMap(CurrencyRate::getCurrencyCode, rate -> rate));

    Map<String, CurrencyRate> etalonMap = etalonRates.stream()
        .collect(Collectors.toMap(CurrencyRate::getCurrencyCode, rate -> rate));

    for (CurrencyRate current : currentRates) {
      CurrencyRate etalon = etalonMap.get(current.getCurrencyCode());

      if (etalon == null) {
        differences.add(String.format(
            "Валюта не найдена в эталоне: %s (%s)",
            current.getCurrencyName(), current.getCurrencyCode()));
        continue;
      }

      double diff = Math.abs(current.getExchangeRate() - etalon.getExchangeRate());
      if (diff > 0.0001) {
        differences.add(String.format(
            "Расхождение в курсе: %s - текущие: %.4f, эталон: %.4f (разница: %.4f)",
            current.getCurrencyCode(), current.getExchangeRate(), etalon.getExchangeRate(), diff));
      }
    }

    for (CurrencyRate etalon : etalonRates) {
      if (!currentMap.containsKey(etalon.getCurrencyCode())) {
        differences.add(String.format(
            "Отсутствует в текущих данных: %s (%s)",
            etalon.getCurrencyName(), etalon.getCurrencyCode()));
      }
    }

    return differences;
  }
}