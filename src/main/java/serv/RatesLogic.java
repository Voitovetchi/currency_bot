package serv;

import dto.CurrentRate;
import org.apache.commons.lang3.StringUtils;

public class RatesLogic{

    public static CurrentRate runProcess(String message) {
        String[] currencies = StringUtils
            .deleteWhitespace(message)
            .toUpperCase()
            .split(",");
        if (currencies.length != 2
            || currencies[0].length() != 3
            || currencies[1].length() != 3) {
            throw new IllegalArgumentException("Введенное сообщение не соответвует формату");
        }
        CurrentRate currentRate = new CurrentRate();
        currentRate.setBaseCurrency(currencies[0]);
        currentRate.setConvertedCurrency(currencies[1]);

        return currentRate;
    }
}
