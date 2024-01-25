package com.sysaid.assignment.strategy;

import com.sysaid.assignment.domain.model.TaskOfTheDay;
import com.sysaid.assignment.domain.model.User;
import com.sysaid.assignment.service.ITaskService;
import com.sysaid.assignment.service.InternalStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TaskOfTheDayService {
    private final InternalStorageService internalStorageService;
    private final ITaskService taskService;

    public TaskOfTheDayStrategy getTaskOfTheDayStrategy(User user) {
        if (user.getUserSettings().isRatingEnabled()) {
            return new RatingTaskStrategy(user, internalStorageService, taskService);
        }
        return new RandomTaskStrategy(user, internalStorageService, taskService);
    }

    public boolean isCurrentTaskOfTheDayExpired(User user) {
        if (user.getTaskOfTheDayKey() == null) {
            return true;
        }

        TaskOfTheDay taskOfTheDay = (TaskOfTheDay) internalStorageService.getTaskById(user.getTaskOfTheDayKey(), user.getName());
        LocalDateTime now = LocalDateTime.now();
        return !now.isAfter(taskOfTheDay.getFetchTime()) || !now.isBefore(taskOfTheDay.getRemainingTime());
    }
}
