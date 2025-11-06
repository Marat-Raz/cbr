package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainPage {
	private final SelenideElement allIndicatorsLink = $(By.xpath("//a[contains(text(), 'Все показатели')]"));
	
	public MainPage openPage() {
		Selenide.open("https://cbr.ru");
		log.info("Открыта главная страница ЦБ РФ");
		return this;
	}
	
	public CurrencyRatesPage clickAllIndicators() {
		allIndicatorsLink.click();
		log.info("Нажата ссылка 'Все показатели'");
		return page(CurrencyRatesPage.class);
	}
}
