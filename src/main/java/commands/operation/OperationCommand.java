package commands.operation;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
public abstract class OperationCommand extends BotCommand {
    private boolean isActive = false;

    OperationCommand(String identifier, String description) {
        super(identifier, description);
    }

    void sendAnswer(AbsSender absSender, Long chatId, String userName, String text)
        throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        absSender.execute(message);
    }

    public abstract String continueAction(String message) throws Exception;

}
