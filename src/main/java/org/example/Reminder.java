package org.example;

import java.util.Date;

public class Reminder {
    private long userId;
    private String message;
    private Date dueDate;

    public Reminder(long userId, String message, Date dueDate) {
        this.userId = userId;
        this.message = message;
        this.dueDate = dueDate;
    }

    public long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
