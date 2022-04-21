package commands.operation;

import dto.HistoricalRate;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.HistoricalRatesService;
import utils.UserMessageValidationUtils;

import static utils.RateExtractionUtils.getRate;

public class HistoricalRatesCommand extends OperationCommand{
    public HistoricalRatesCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public String continueAction(String message) throws Exception {
        String[] values = StringUtils.deleteWhitespace(message).toUpperCase().split(",");
        HistoricalRate historicalRate = new HistoricalRate();
        historicalRate.setBaseCurrency(values[0]);
        historicalRate.setConvertedCurrency(values[1]);
        if (!UserMessageValidationUtils.dateIsValid(values[2])) {
            throw new Exception("Дата должна соотвествовать формату yyyy-MM-dd, например, 2020-01-30. Попробуйте еще раз.");
        }
        historicalRate.setDate(values[2]);
        String responseBody = HistoricalRatesService.sendRequest(historicalRate);
        double rate = getRate(historicalRate, responseBody);
        setActive(false);

        return "Курс " + historicalRate.getBaseCurrency() + " к " + historicalRate.getConvertedCurrency() + " на " + historicalRate.getDate() + " составляет " + rate;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        setActive(true);
        sendAnswer(absSender, chat.getId(), "", "", user.getUserName(),
            """
                Введите конвертируемую валюту, основную валюту и дату курса. Данные должны вводиться через запятую, дата должна соответсвовать формату yyyy-MM-dd
                Например, если необходимо получить курс евро относительно доллара на 2012-12-12, сообщение должно выглядеть следующим образом
                EUR,USD,2012-12-12""");
    }
}
