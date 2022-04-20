package dto;

public interface Rater {
    String getBaseCurrency();

    void setBaseCurrencyRate(double rate);

    String getConvertedCurrency();

    void setConvertedCurrencyRate(double rate);

    default String getDate() {
        return "";
    }

    double getBaseCurrencyRate();

    double getConvertedCurrencyRate();
}
