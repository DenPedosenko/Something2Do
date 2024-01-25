package com.sysaid.assignment.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskOfTheDay extends TaskDao {
    private final LocalDateTime fetchTime;
    private final LocalDateTime remainingTime;

    public TaskOfTheDay(Task task, String user, Boolean isCompleted, Boolean isWished, int rating, LocalDateTime fetchTime, LocalDateTime remainingTime) {
        super(task, user, isCompleted, isWished, rating);
        this.fetchTime = fetchTime;
        this.remainingTime = remainingTime;
    }
}
