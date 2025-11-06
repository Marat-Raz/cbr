import static methods.Methods.*;
import static org.testng.Assert.*;

import dto.CurrencyRate;
import java.util.List;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import setup.BaseTests;

public class CurrencyTests extends BaseTests {

  @Test
  @Description("Тест сохраняет текущие курсы CNY, USD, EUR в JSON файл")
  public void testSaveCurrentRates() {
    allIndicatorsSteps.openKeyIndicatorsPage();

    List<CurrencyRate> rates = allIndicatorsSteps.getAllCurrencyRates();
    saveRatesToJson(rates, CURRENT_RATES_FILE);

    assertFalse(rates.isEmpty());
    assertTrue(rates.size() >= 3);
  }

  @Test
  @Description("Сравнить текущие курсы с эталонными и вывести расхождения")
  public void testCompareWithEtalon() {
    allIndicatorsSteps.openKeyIndicatorsPage();

    List<CurrencyRate> currentRates = allIndicatorsSteps.getAllCurrencyRates();
    List<CurrencyRate> etalonRates = loadRatesFromJson(ETALON_RATES_FILE);

    List<String> differences = compareRates(currentRates, etalonRates);

    saveRatesToJson(currentRates, "currentRates.json");

    if (!differences.isEmpty()) {
      System.out.printf(
          """
              %n%s
              ОБНАРУЖЕНЫ РАСХОЖДЕНИЯ С ЭТАЛОНОМ
              %s
              %s
              %s
              ВСЕГО РАСХОЖДЕНИЙ: %d
              %s
              """, "=".repeat(50), "=".repeat(50),
          String.join("\n", differences), "=".repeat(50),
          differences.size(), "=".repeat(50));
    } else {
      System.out.println("✓ Расхождений с эталоном не обнаружено");
    }

    System.out.println("\nТекущие курсы сохранены в: currentRates.json");
    System.out.println("Эталонные курсы загружены из: " + ETALON_RATES_FILE);
  }

  @Test
  @Description("Тест проверяет корректную загрузку таблицы с курсами валют")
  public void testCurrencyTableLoads() {
    allIndicatorsSteps.openKeyIndicatorsPage();

    int currencyCount = allIndicatorsSteps.getCurrencyCount();
    assertTrue(
        currencyCount >= 3,
        "В таблице должно быть как минимум 3 валюты");
  }

  @Test
  @Description("Тест проверяет корректность извлечения данных о валютах")
  public void testCurrencyDataStructure() {
    allIndicatorsSteps.openKeyIndicatorsPage();

    List<CurrencyRate> rates = allIndicatorsSteps.getAllCurrencyRates();

    for (CurrencyRate rate : rates) {
      assertNotNull(rate.getCurrencyName());
      assertNotNull(rate.getCurrencyCode());
      assertTrue(rate.getExchangeRate() > 0);
      assertTrue(rate.getUnit() > 0);
    }
  }

}
