package commands.operation;

import dto.CurrentRate;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.CurrentRatesService;

import static utils.RateExtractionUtils.getRate;


public class RatesCommand extends OperationCommand{

    public RatesCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        setActive(true);
        sendAnswer(absSender, chat.getId(), "", "", user.getUserName(),
            """
                Введите конвертируемую валюту и основную валюту. Данные должны вводиться через запятую.
                Например, если необходимо получить курс евро относительно доллара, сообщение должно выглядеть следующим образом
                EUR,USD""");
    }

    @Override
    public String continueAction(String message) throws Exception {
        String[] currencies = StringUtils.deleteWhitespace(message).toUpperCase().split(",");
        CurrentRate currentRate = new CurrentRate();
        currentRate.setBaseCurrency(currencies[0]);
        currentRate.setConvertedCurrency(currencies[1]);

        String responseBody = CurrentRatesService.sendRequest(currentRate);

        double rate = getRate(currentRate, responseBody);
        setActive(false);

        return "Курс " + currentRate.getBaseCurrency() + " к " + currentRate.getConvertedCurrency() + " равен " + rate;
    }


}
