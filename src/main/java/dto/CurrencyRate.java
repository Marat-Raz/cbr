package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRate {
	private String currencyName;
	private String currencyCode;
	private double exchangeRate;
	private int unit;

	@Override
	public String toString() {
		return String.format("%s (%s): %.4f за %d", currencyName, currencyCode, exchangeRate, unit);
	}
}