package elements;

import static com.codeborne.selenide.Selenide.$;
import static methods.Methods.getElementByHref;

import com.codeborne.selenide.SelenideElement;

public class MainPageElements {

  public SelenideElement btnConfirm = $("button[class*='medium']");
  public SelenideElement LinkAllIndicators = getElementByHref("/key-indicators/");

}
