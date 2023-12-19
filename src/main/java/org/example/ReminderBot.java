package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class ReminderBot extends TelegramLongPollingBot {
    private final String botUsername = "@very_cool_ReminderBot";
    private final String botToken = "6799379167:AAFERe3GjtIYBLczcib9FQlHe0fQV-n8tiI";
    private final ReminderManager reminderManager = new ReminderManager(new HandlerBot(this));

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            long userId = message.getFrom().getId();
            String text = message.getText();
            if (text.equals("/start")){
                sendMessage(
                        chatId,
                        "Привет!\nЯ помогу тебе ничего не забыть, просто создай напоминание с помощью:\n/remind <сообщение> <дата формата 01.01.2023> <время формата 00:00>"
                );

            }
            else if (text.startsWith("/remind")) {
                String[] commandParts = text.split(" ");
                if (commandParts.length == 4) {
                    String reminderMessage = commandParts[1];
                    String dateInput = commandParts[2];
                    String timeInput = commandParts[3];

                    String dateTimeInput = dateInput + " " + timeInput;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    try {
                        Date dueDate = dateFormat.parse(dateTimeInput);

                        reminderManager.addReminder(userId, reminderMessage, dueDate);
                        sendMessage(
                                chatId,
                                "Напоминалка готова!"
                        );
                    } catch (ParseException e) {
                        sendMessage(
                                chatId,
                                "Неверный форматы даты и времени. попробуй: /remind <сообщение> <дата формата 01.01.2023> <время формата 00:00>"
                        );
                    }
                }
                else {
                    sendMessage(
                            chatId,
                            "Неверная команда. попробуй: /remind <сообщение> <дата формата 01.01.2023> <время формата 00:00>"
                    );
                }
            }
            else if (text.equals("/myreminders")) {
                List<Reminder> userReminders = reminderManager.getRemindersByUserId(userId);
                StringBuilder reminderText = new StringBuilder("Существующие напоминалки:\n");
                for (Reminder reminder : userReminders) {
                    reminderText.append(reminder.getMessage()).append(" - ").append(reminder.getDueDate()).append("\n");
                }
                sendMessage(chatId, reminderText.toString());
            }
        }
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            executeAsync(message);
        } catch (TelegramApiException e) {
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
