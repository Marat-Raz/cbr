package setup;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.webdriver;

import methods.Methods;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import steps.AllIndicatorsSteps;
import steps.MainPageSteps;

public class BaseTests {

  protected Methods methods = new Methods();
  protected MainPageSteps mainPageSteps = new MainPageSteps();
  protected AllIndicatorsSteps allIndicatorsSteps = new AllIndicatorsSteps();

  protected static final String CURRENT_RATES_FILE = "current_rates.json";
  protected static final String ETALON_RATES_FILE = "etalon.json";

  @BeforeMethod
  public void setUp() {
    timeout = 15000;
    browser = "chrome";
    headless = false;
  }

  @AfterMethod
  public void tearDown() {
    if (webdriver() != null) {
      closeWebDriver();
    }
  }
}
