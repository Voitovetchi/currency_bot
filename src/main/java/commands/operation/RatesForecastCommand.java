package commands.operation;

import business.RateForecast;
import dto.HistoricalRate;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.HistoricalRatesService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static utils.RateExtractionUtils.getRate;

public class RatesForecastCommand extends OperationCommand{
    public RatesForecastCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public String continueAction(String message) throws Exception {
        String[] currencies = StringUtils.deleteWhitespace(message).toUpperCase().split(",");
        HistoricalRate historicalRate = new HistoricalRate();
        historicalRate.setBaseCurrency(currencies[0]);
        historicalRate.setConvertedCurrency(currencies[1]);
        historicalRate.setDate(LocalDate.now().toString());

        RateForecast rateForecast = new RateForecast();
        for (int i = 0; i < 5; i++) {
            String responseBody = HistoricalRatesService.sendRequest(historicalRate);
            rateForecast.getPreviousRates()[i] = getRate(historicalRate, responseBody);
            historicalRate.setDate(LocalDateTime.now().minusDays(i+1).toLocalDate().toString());
        }
        rateForecast.doForecast();

        StringBuilder responseMessage = new StringBuilder("Прогнозируемые курсы ")
            .append(historicalRate.getBaseCurrency())
            .append(" относительно ")
            .append(historicalRate.getConvertedCurrency())
            .append(" на следующие 5 дней\n");
        for (int i = 0; i < rateForecast.getForecastRates().length; i++) {
            responseMessage
                .append(LocalDateTime.now().plusDays(i+1).toLocalDate().toString())
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
        sendAnswer(absSender, chat.getId(), user.getUserName(),
            """
                Введите конвертируемую валюту и основную валюту. Данные должны вводиться через запятую.
                Например, если необходимо получить курс евро относительно доллара, сообщение должно выглядеть следующим образом
                EUR,USD""");
    }
}
