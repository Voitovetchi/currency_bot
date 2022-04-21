import commands.NonCommand;
import commands.operation.*;
import commands.service.HelpCommand;
import commands.service.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot extends TelegramLongPollingCommandBot {

    private static final String BOT_NAME = System.getenv("BOT_NAME");
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");

    private final NonCommand nonCommand;
    private final List<OperationCommand> operationCommands = new ArrayList<>();

    public Bot() {
        super();
        nonCommand = new NonCommand();
        register(new StartCommand("start", "Старт"));
        register(new HelpCommand("help", "Помощь"));

        RatesCommand ratesCommand = new RatesCommand("rates", "Курс валют");
        register(ratesCommand);

        HistoricalRatesCommand historicalRatesCommand = new HistoricalRatesCommand("historicalrates", "Курс валют в предыдущие дни");
        register(historicalRatesCommand);

        RatesForecastCommand forecastCommand = new RatesForecastCommand("forecast", "Прогноз курса валют");
        register(forecastCommand);

        RatesConversionCommand conversionCommand = new RatesConversionCommand("conversion", "Конверсия валют");
        register(conversionCommand);

        operationCommands.addAll(Arrays.asList(ratesCommand, historicalRatesCommand, forecastCommand, conversionCommand));
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        try {
            for (OperationCommand command : operationCommands) {
                if (command.isActive()) {
                    answer = command.continueAction(update.getMessage().getText());
                }
            }
        } catch (Exception e) {
            answer = e.getMessage();
        }

        setAnswer(chatId, userName, answer);
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException ignored) {

        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
