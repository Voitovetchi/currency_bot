package dto;

import lombok.Data;

@Data
public class HistoricalRate implements Rater {
    String baseCurrency;
    String convertedCurrency;
    double baseCurrencyRate;
    double convertedCurrencyRate;
    String date;
}
