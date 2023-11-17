package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {
    public static void main(String[] args) {
        String botUsername = "very_cool_ReminderBot";
        String botToken = "6799379167:AAFERe3GjtIYBLczcib9FQlHe0fQV-n8tiI";

        try {
            ReminderBot bot = new ReminderBot(botUsername, botToken);
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
