package commands.operation;

import dto.CurrentRate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.CurrentRatesService;
import service.Service;

import java.io.IOException;
import java.net.URISyntaxException;

import static utils.RateExtractionUtils.getRate;

public class RatesConversionCommand extends OperationCommand{
    public RatesConversionCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public String continueAction(String message) throws URISyntaxException, IOException, InterruptedException {
        String[] values = message.split(",");
        CurrentRate currentRate = new CurrentRate();
        currentRate.setBaseCurrency(values[0]);
        currentRate.setConvertedCurrency(values[1]);

        Service currentRatesService = new CurrentRatesService();
        String responseBody = currentRatesService.sendRequest(currentRate);

        double rate = getRate(currentRate, responseBody);

        setActive(false);
        return Double.toString(rate * Double.parseDouble(values[2]));
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        setActive(true);
        sendAnswer(absSender, chat.getId(), "", "", user.getUserName(),
            "Введите конвертируемую валюту, основную валюту и количество конвертируемой валюты.");
    }
}
