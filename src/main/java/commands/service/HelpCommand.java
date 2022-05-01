package commands.service;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand {
    public HelpCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendAnswer(absSender, chat.getId(), user.getUserName(),
            """
                Доступные команды:
                1. Предоставление курса валют - /rates.
                2. Предоставление курса валют за предыдущие дни - /historicalrates.
                3. Прогноз курса валют - /forecast.
                4. Конверсия валют - /conversion.""");
    }
}
