package methods;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class Methods {
	
	@Step("Возвращает элемент с href")
	public static SelenideElement getElementByHref(String href) {
		return $("[href='" + href + "']");
	}
}
