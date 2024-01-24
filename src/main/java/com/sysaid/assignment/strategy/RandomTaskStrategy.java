package com.sysaid.assignment.strategy;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.mapper.TaskMapper;
import com.sysaid.assignment.service.ITaskService;
import com.sysaid.assignment.service.InternalStorageService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class RandomTaskStrategy implements TaskOfTheDayStrategy {
    private final User user;
    private final InternalStorageService internalStorageService;
    private final ITaskService taskService;

    @Override
    public void setTaskOfTheDay() {
        Task task = taskService.getRandomTask();
        user.setTaskOfTheDayKey(task.key());
        internalStorageService.update(user);
        internalStorageService.save(TaskMapper.ToTaskOfTheDay(task, user.getName(), false, false,
                0, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

    }
}
