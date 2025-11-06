package elements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class AllIndicatorsElements {

  public SelenideElement currencyTableHeader = $x("//td[contains(text(), 'валюта')]");
  public SelenideElement currencyTable = currencyTableHeader.$x("./ancestor::table");
  public ElementsCollection currencyRows = currencyTable.$$("tbody tr:not(.denotements)");

  public By currencyNameLocator = By.cssSelector("div.col-md-5");
  public By currencyCodeLocator = By.cssSelector("div._subinfo");
  public By exchangeRateLocator = By.cssSelector("td._bold._end.mono-num");
}
