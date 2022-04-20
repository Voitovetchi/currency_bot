package commands.operation;

import business.RateForecast;
import dto.HistoricalRate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.HistoricalRatesService;
import service.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static utils.RateExtractionUtils.getRate;

public class RatesForecastCommand extends OperationCommand{
    public RatesForecastCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public String continueAction(String message) throws URISyntaxException, IOException, InterruptedException {
        String[] currencies = message.split(",");
        HistoricalRate historicalRate = new HistoricalRate();
        historicalRate.setBaseCurrency(currencies[0]);
        historicalRate.setConvertedCurrency(currencies[1]);
        historicalRate.setDate(LocalDate.now().toString());

        Service historicalRateService = new HistoricalRatesService();
        RateForecast rateForecast = new RateForecast();
        for (int i = 0; i < 5; i++) {
            String responseBody = historicalRateService.sendRequest(historicalRate);
            rateForecast.getPreviousRates()[i] = getRate(historicalRate, responseBody);
            historicalRate.setDate(LocalDateTime.now().minusDays(i+1).toLocalDate().toString());
        }
        rateForecast.doForecast();

        StringBuilder responseMessage = new StringBuilder("Предполагаемые курсы на следуюзие 5 дней\n");
        for (int i = 0; i < rateForecast.getForecastRates().length; i++) {
            responseMessage.append(LocalDateTime.now().plusDays(i+1).toLocalDate().toString())
                .append(" - ")
                .append(rateForecast.getForecastRates()[i])
                .append("\n");

        }
        setActive(false);
        return responseMessage.toString();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        setActive(true);
        sendAnswer(absSender, chat.getId(), "", "", user.getUserName(),
            "Введите основную валюту и валюту конвертации.");
    }
}
