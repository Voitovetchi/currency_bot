package commands.operation;

import dto.CurrentRate;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.CurrentRatesService;

import static utils.RateExtractionUtils.getRate;

public class RatesConversionCommand extends OperationCommand{
    public RatesConversionCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public String continueAction(String message) throws Exception {
        String[] values = StringUtils.deleteWhitespace(message).toUpperCase().split(",");
        CurrentRate currentRate = new CurrentRate();
        currentRate.setBaseCurrency(values[0]);
        currentRate.setConvertedCurrency(values[1]);

        String responseBody = CurrentRatesService.sendRequest(currentRate);

        double rate = getRate(currentRate, responseBody);

        setActive(false);
        return values[2] + " " + currentRate.getBaseCurrency() + " = " + rate * Double.parseDouble(values[2]) + " " + currentRate.getConvertedCurrency();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        setActive(true);
        sendAnswer(absSender, chat.getId(), "", "", user.getUserName(),
            """
                Введите конвертируемую валюту, основную валюту и количество конвертируемой валюты. Данные должны вводиться через запятую.
                Например, если необходимо конвертировать 10 евро в доллар, сообщение должно выглядеть следующим образом
                EUR,USD,10""");
    }
}
