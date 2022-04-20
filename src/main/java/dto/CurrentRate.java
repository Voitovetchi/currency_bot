package dto;

import lombok.Data;

@Data
public class CurrentRate implements Rater{
    String baseCurrency;
    String convertedCurrency;
    double baseCurrencyRate;
    double convertedCurrencyRate;
}
