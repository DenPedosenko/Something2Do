package com.sysaid.assignment.mapper;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.domain.TaskDao;
import com.sysaid.assignment.domain.TaskOfTheDay;

import java.time.LocalDateTime;

public class TaskMapper {
    public static TaskDao toDao(Task task, String user, boolean isCompleted, boolean isWished, int rating) {
        return new TaskDao(task, user, isCompleted, isWished, rating);
    }

    public static TaskOfTheDay ToTaskOfTheDay(Task task, String user, boolean isCompleted, boolean isWished, int rating,
                                                 LocalDateTime fetchTime, LocalDateTime remainingTime) {
        return new TaskOfTheDay(task, user, isCompleted, isWished, rating, fetchTime, remainingTime);
    }

    public static Task toDomain(TaskDao taskDao) {
        return taskDao.getTask();
    }
}
