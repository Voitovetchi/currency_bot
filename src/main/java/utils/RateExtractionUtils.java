package utils;

import dto.HistoricalRate;
import dto.Rater;

public class RateExtractionUtils {

    public static void fillWIthRates(Rater rate, String response) {
        if (!rate.getBaseCurrency().equals("EUR")) {
            String baseCur = response.substring(response.indexOf(rate.getBaseCurrency()) + 5);
            rate.setBaseCurrencyRate(Double.parseDouble(baseCur.substring(0, baseCur.indexOf(","))));
        } else {
            rate.setBaseCurrencyRate(1);
        }
        if (!rate.getConvertedCurrency().equals("EUR")) {
            String convCur = response.substring(response.indexOf(rate.getConvertedCurrency()) + 5);
            rate.setConvertedCurrencyRate(Double.parseDouble(convCur.substring(0, convCur.indexOf(","))));
        } else {
            rate.setConvertedCurrencyRate(1);
        }
    }

    public static double getRate(Rater historicalRate, String responseBody) {
        responseBody = responseBody.substring(responseBody.indexOf("rates"));
        RateExtractionUtils.fillWIthRates(historicalRate, responseBody);
        return 1 / historicalRate.getBaseCurrencyRate() * historicalRate.getConvertedCurrencyRate();
    }
}
