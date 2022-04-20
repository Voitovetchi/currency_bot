package commands.operation;

import dto.HistoricalRate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.CurrentRatesService;
import service.HistoricalRatesService;
import service.Service;
import utils.RateExtractionUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static utils.RateExtractionUtils.getRate;

public class HistoricalRatesCommand extends OperationCommand{
    public HistoricalRatesCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public String continueAction(String message) throws URISyntaxException, IOException, InterruptedException {
        String[] values = message.split(",");
        HistoricalRate historicalRate = new HistoricalRate();
        historicalRate.setBaseCurrency(values[0]);
        historicalRate.setConvertedCurrency(values[1]);
        historicalRate.setDate(values[2]);
        Service historicalRatesService = new HistoricalRatesService();
        String responseBody = historicalRatesService.sendRequest(historicalRate);
        double rate = getRate(historicalRate, responseBody);
        setActive(false);

        return "Курс на " + historicalRate.getDate() + " " + rate;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        setActive(true);
        sendAnswer(absSender, chat.getId(), "", "", user.getUserName(),
            "Введите основную валюту, валюту конвертации дату курса.");
    }
}
