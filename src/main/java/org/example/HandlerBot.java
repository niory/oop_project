package org.example;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
public class HandlerBot {
    private final ReminderBot bot;

    public HandlerBot(ReminderBot bot) {
        this.bot = bot;
    }

    public void sendTextMessage(long userId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.setText(text);

        try {
            bot.executeAsync(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
