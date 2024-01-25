package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.model.TaskDao;
import com.sysaid.assignment.domain.model.User;

import java.util.List;

public interface ITaskStorageService extends DataStorage {
    List<TaskDao> getAllUsersCompletedTasks(User user);

    List<TaskDao> getAllUsersTasks(User user);

    List<TaskDao> getAllUsersWishedTasks(User user);

    List<TaskDao> getActiveUserTasks(String userName);

    TaskDao getTaskById(String taskId, String userName);

    void markTaskAsCompleted(String taskId, String userName);

    void markTaskAsWished(String taskId, String userName);

    void removeTask(String taskId, String userName);
}

