package org.example;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderManager {

    private List<Reminder> reminders;
    private final HandlerBot HandlerBot;
    public ReminderManager(HandlerBot HandlerBot) {
        this.reminders = new ArrayList<>();
        this.HandlerBot = HandlerBot;
        startReminderScheduler();
    }

    public void addReminder(long userId, String message, Date dueDate) {
        Reminder reminder = new Reminder(userId, message, dueDate);
        reminders.add(reminder);
    }

    public List<Reminder> getRemindersByUserId(long userId) {
        List<Reminder> userReminders = new ArrayList<>();
        for (Reminder reminder : reminders) {
            if (reminder.getUserId() == userId) {
                userReminders.add(reminder);
            }
        }
        return userReminders;
    }

    private void startReminderScheduler() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date now = new Date();
                for (Reminder reminder : reminders) {
                    if (now.after(reminder.getDueDate())) {
                        long userId = reminder.getUserId();
                        HandlerBot.sendTextMessage(userId, "НАПОМИНАНИЕ: \n" + reminder.getMessage());
                        reminders.remove(reminder);
                    }
                }
            }
        }, 0, 1000);
    }
}