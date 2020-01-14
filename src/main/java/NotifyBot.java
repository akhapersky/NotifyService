import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class NotifyBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new NotifyBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        // TODO
    }

    @Override
    public String getBotUsername() {
        return "Family Notification Bot";
    }

    @Override
    public String getBotToken() {
        return "1010061028:AAF3VUXeu9SDoPxWUIhRdBiNN1dIkeYhNLE";
    }
}
