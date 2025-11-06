package steps;

import com.codeborne.selenide.Selenide;
import elements.MainPageElements;
import io.qameta.allure.Step;

public class MainPageSteps {

  private final MainPageElements mainPageElements = new MainPageElements();

  @Step("Открыть главную страницу ЦБ РФ")
  public void openPage() {
    Selenide.open("https://cbr.ru");
    if (mainPageElements.btnConfirm.isDisplayed()) {
      mainPageElements.btnConfirm.click();
    }
  }

  @Step("Нажать на ссылку 'Все показатели'")
  public void clickAllIndicators() {
    mainPageElements.LinkAllIndicators.click();
  }

}
