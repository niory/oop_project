package org.example;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Date;
import java.util.List;

public class ReminderBot extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;
    private ReminderManager reminderManager;

    public ReminderBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.reminderManager = new ReminderManager();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            long userId = message.getFrom().getId();
            String text = message.getText();

            if (text.startsWith("/remind")) {
                // Parse the command and add a reminder
                String[] commandParts = text.split(" ");
                if (commandParts.length == 4) {
                    String reminderMessage = commandParts[1];
                    String dateInput = commandParts[2];
                    String timeInput = commandParts[3];

                    String dateTimeInput = dateInput + " " + timeInput;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD MM YYYY hh:mm");
                    try {
                        Date dueDate = dateFormat.parse(dateTimeInput);

                        reminderManager.addReminder(userId, reminderMessage, dueDate);
                        sendTextMessage(chatId, "напоминалка готова!");
                    } catch (ParseException e) {
                        sendTextMessage(chatId, "неверный форматы даты и времени. попробуй: /remind <сообщение> <дата формата 01 01 2023> <время формата 00:00>");
                    }
                } else {
                    sendTextMessage(chatId, "неверная команда. попробуй: /remind <сообщение> <дата формата 01 01 2023> <время формата 00:00>");
                }
            } else if (text.equals("/myreminders")) {
                // Get and send user's reminders
                List<Reminder> userReminders = reminderManager.getRemindersByUserId(userId);
                StringBuilder reminderText = new StringBuilder("существующие напоминалки:\n");
                for (Reminder reminder : userReminders) {
                    reminderText.append(reminder.getMessage()).append(" - ").append(reminder.getDueDate()).append("\n");
                }
                sendTextMessage(chatId, reminderText.toString());
            }
        }
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
