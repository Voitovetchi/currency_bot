package serv;

import dto.HistoricalRate;
import org.apache.commons.lang3.StringUtils;
import utils.UserMessageValidationUtils;

public class HistoricalRatesLogic {
    public static HistoricalRate runProcess(String message) throws Exception {
        String[] values = StringUtils.deleteWhitespace(message).toUpperCase().split(",");
        HistoricalRate historicalRate = new HistoricalRate();
        historicalRate.setBaseCurrency(values[0]);
        historicalRate.setConvertedCurrency(values[1]);
        if (values.length != 3 || values[0].length() != 3 || values[1].length() != 3) {
            throw new IllegalArgumentException("Введенное сообщение не соответвует формату");
        }
        if (!UserMessageValidationUtils.dateIsValid(values[2])) {
            throw new IllegalArgumentException("Дата должна соотвествовать формату yyyy-MM-dd, например, 2020-01-30. Попробуйте еще раз.");
        }
        historicalRate.setDate(values[2]);

        return historicalRate;
    }
}
