package org.example;

public class Main {
    public static void main(String[] args) {
        String botUsername = "very_cool_ReminderBot";
        String botToken = "6799379167:AAFERe3GjtIYBLczcib9FQlHe0fQV-n8tiI";

        ReminderBot bot = new ReminderBot(botUsername, botToken);
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
