package commands;

public class NonCommand {

    public String nonCommandExecute(Long chatId, String userName, String text) {
        return "Простите, я не понимаю Вас. Возможно, Вам поможет /help";
    }
}
