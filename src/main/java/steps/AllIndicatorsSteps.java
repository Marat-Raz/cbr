package steps;

import static methods.Methods.waitShouldBeVisible;

import dto.CurrencyRate;
import elements.AllIndicatorsElements;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;

public class AllIndicatorsSteps {

  private final AllIndicatorsElements allIndicatorsElements = new AllIndicatorsElements();
  protected MainPageSteps mainPageSteps = new MainPageSteps();

  @Step("Открыть страницу 'Ключевые показатели'")
  public void openKeyIndicatorsPage() {
    mainPageSteps.openPage();
    mainPageSteps.clickAllIndicators();
    waitForTableLoaded();
  }

  @Step("Проверить, что таблица с курсами загружена")
  public void waitForTableLoaded() {
    waitShouldBeVisible(allIndicatorsElements.currencyTableHeader);
    waitShouldBeVisible(allIndicatorsElements.currencyTable);
  }

  @Step("Получить все курсы валют из таблицы")
  public List<CurrencyRate> getAllCurrencyRates() {
    List<CurrencyRate> rates = new ArrayList<>();

    int rowCount = allIndicatorsElements.currencyRows.size();
    for (int i = 0; i < rowCount; i++) {
      try {
        CurrencyRate rate = extractCurrencyRateFromRow(i);
        rates.add(rate);
      } catch (Exception e) {
        System.out.println( "Ошибка при извлечении курса из строки " + i + ": " + e.getMessage());
      }
    }

    return rates;
  }

  @Step("Извлечь курс валюты из строки {rowIndex}")
  private CurrencyRate extractCurrencyRateFromRow(int rowIndex) {
    com.codeborne.selenide.SelenideElement row = allIndicatorsElements.currencyRows.get(rowIndex);

    String name = row.$(allIndicatorsElements.currencyNameLocator).getText().trim();
    String codeText = row.$(allIndicatorsElements.currencyCodeLocator).getText().trim();
    String rateText = row.$$(allIndicatorsElements.exchangeRateLocator).get(0).getText()
        .replace(",", ".")
        .replaceAll("[^0-9.]", "");

    String code = codeText.replaceAll("\\d+\\s*", "").trim();
    int unit = Integer.parseInt(codeText.replaceAll("[^0-9]", ""));
    double rate = Double.parseDouble(rateText);

    return new CurrencyRate(name, code, rate, unit);
  }


  @Step("Получить количество валют в таблице")
  public int getCurrencyCount() {
    return allIndicatorsElements.currencyRows.size();
  }
}
