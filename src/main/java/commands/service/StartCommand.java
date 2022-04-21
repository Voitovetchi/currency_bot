package commands.service;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends ServiceCommand{

    public StartCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), user.getUserName(),
            """
                Здравствуйте. Давайте начнём!
                Данный бот предоставляет такие возможности как:
                1. Предоставление курса валют - /rates.
                2. Предоставление курса валют за предыдущие дни - /historicalrates.
                3. Прогноз курса валют - /forecast.
                4. Конверсия валют - /conversion.
                Если Вам нужна помощь, нажмите /help.
                Чтобы еще раз получить это сообщение нажмите /start.""");
    }
}
