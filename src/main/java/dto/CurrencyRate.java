package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRate {
	@JsonProperty("currency_name")
	private String currencyName;
	
	@JsonProperty("currency_code")
	private String currencyCode;
	
	@JsonProperty("exchange_rate")
	private double exchangeRate;
	
	@JsonProperty("unit")
	private int unit;
	
	@Override
	public String toString() {
		return String.format("%s (%s): %.4f за %d", currencyName, currencyCode, exchangeRate, unit);
	}
}