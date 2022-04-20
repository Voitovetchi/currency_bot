package commands.operation;

import dto.CurrentRate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.CurrentRatesService;
import service.Service;
import utils.RateExtractionUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static utils.RateExtractionUtils.getRate;


public class RatesCommand extends OperationCommand{

    public RatesCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        setActive(true);
        sendAnswer(absSender, chat.getId(), "", "", user.getUserName(),
            "Введите основную валюту и валюту конвертации.");
    }

    @Override
    public String continueAction(String message) throws URISyntaxException, IOException, InterruptedException {
        String[] currencies = message.split(",");
        CurrentRate currentRate = new CurrentRate();
        currentRate.setBaseCurrency(currencies[0]);
        currentRate.setConvertedCurrency(currencies[1]);

        Service currentRatesService = new CurrentRatesService();
        String responseBody = currentRatesService.sendRequest(currentRate);

        double rate = getRate(currentRate, responseBody);
        setActive(false);

        return "Курс " + rate;
    }


}
