package org.example;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderManager {
    private List<Reminder> reminders;

    public ReminderManager() {
        this.reminders = new ArrayList<>();
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
                // Check and send reminders
                Date now = new Date();
                for (Reminder reminder : reminders) {
                    if (now.after(reminder.getDueDate())) {
                        //send reminder to user
                        System.out.println("Reminder for user " + reminder.getUserId() + ": " + reminder.getMessage());
                        //implement sending a message to the user via tg API here
                        reminders.remove(reminder); // remove the reminder after sending
                    }
                }
            }
        }, 0, 1000); // check every second adjust as needed
    }
}