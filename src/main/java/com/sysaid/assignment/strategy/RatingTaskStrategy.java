package com.sysaid.assignment.strategy;

import com.sysaid.assignment.domain.model.TaskDao;
import com.sysaid.assignment.domain.model.TaskOfTheDay;
import com.sysaid.assignment.domain.model.User;
import com.sysaid.assignment.mapper.TaskMapper;
import com.sysaid.assignment.service.ITaskService;
import com.sysaid.assignment.service.InternalStorageService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class RatingTaskStrategy implements TaskOfTheDayStrategy {
    private final User user;
    private final InternalStorageService internalStorageService;
    private final ITaskService taskService;
    private final Random random = new Random();

    @Override
    public void setTaskOfTheDay() {
        TaskDao task = getTaskOfTheDay();
        storeTaskOfTheDay(task);
    }

    private void storeTaskOfTheDay(TaskDao task) {
        TaskOfTheDay taskOfTheDay = TaskMapper.ToTaskOfTheDay(task.getTask(), user.getName(), task.getIsCompleted(), task.getIsWished(),
                task.getRating(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        user.setTaskOfTheDayKey(taskOfTheDay.getTask().key());
        internalStorageService.update(user);
        internalStorageService.update(taskOfTheDay);
    }

    public TaskDao getTaskOfTheDay() {
        List<TaskDao> tasks = internalStorageService.getAllUsersTasks(user);
        if (tasks.isEmpty()) {
            return getRandomTask();
        }

        List<TaskDao> sortedTasks = new ArrayList<>(tasks);
        sortedTasks.sort(Collections.reverseOrder());

        int totalTasks = sortedTasks.size();
        double[] cumulativeProbability = new double[totalTasks];
        double sumProbability = 0;

        for (int i = 0; i < totalTasks; i++) {
            double probability = getCategoryProbability(i + 1);
            sumProbability += probability;
            cumulativeProbability[i] = sumProbability;
        }
        double randomValue = random.nextDouble() * 100;

        for (int i = 0; i < totalTasks; i++) {
            if (randomValue <= cumulativeProbability[i]) {
                return sortedTasks.get(i);
            }
        }

        return getRandomTask();
    }

    private TaskDao getRandomTask() {
        TaskDao taskDao = TaskMapper.toDao(taskService.getRandomTask(), user.getName(), false, false, 0);
        internalStorageService.save(taskDao);
        return taskDao;
    }

    private double getCategoryProbability(int category) {
        return switch (category) {
            case 1, 2 -> 20.0;
            case 3 -> 10.0;
            case 4, 5 -> 5.0;
            default -> 40.0;
        };
    }
}
