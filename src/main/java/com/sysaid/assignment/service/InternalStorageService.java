package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.DataStorage;
import com.sysaid.assignment.domain.model.TaskDao;
import com.sysaid.assignment.domain.model.User;
import com.sysaid.assignment.repository.TaskRepository;
import com.sysaid.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InternalStorageService implements DataStorage {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;



    public void save(Object object) {
        if (object instanceof TaskDao) {
            taskRepository.saveTask((TaskDao) object);
        }
        if (object instanceof User) {
            userRepository.saveUser((User) object);
        }
    }

    public List<TaskDao> getAllUsersCompletedTasks(User user) {
        return taskRepository.getAllUsersTasks(user.getName()).stream().filter(TaskDao::getIsCompleted).toList();

    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
    public List<TaskDao> getAllUsersTasks(User user) {
        return taskRepository.getAllUsersTasks(user.getName());
    }

    public List<TaskDao> getAllUsersWishedTasks(User user) {
        return taskRepository.getAllUsersTasks(user.getName()).stream().filter(task -> !task.getIsCompleted() &&
                task.getIsWished() && !Objects.equals(task.getTask().key(), user.getTaskOfTheDayKey())).toList();

    }

    public List<TaskDao> getActiveUserTasks(String userName) {
        return taskRepository.getAllUsersTasks(userName).stream().filter(task -> !task.getIsCompleted() &&
                !task.getIsWished() && !Objects.equals(task.getTask().key(), userRepository.getUserByName(userName).getTaskOfTheDayKey())).toList();
    }

    public TaskDao getTaskById(String taskId, String userName) {
        return taskRepository.getTaskById(taskId, userName);
    }

    public User getUserByName(String userName) {
        return userRepository.getUserByName(userName);
    }

    @Override
    public void update(Object updatedObject) {
        if (updatedObject instanceof TaskDao) {
            taskRepository.updateTask((TaskDao) updatedObject);
        }
        if (updatedObject instanceof User) {
            userRepository.updateUser((User) updatedObject);
        }
    }

    public void markTaskAsCompleted(String taskId, String userName) {
        TaskDao task = taskRepository.getTaskById(taskId, userName);
        task.setIsCompleted(true);
        task.setRating(task.getRating() + 2);
        taskRepository.updateTask(task);
    }

    public void markTaskAsWished(String taskId, String userName) {
        TaskDao task = taskRepository.getTaskById(taskId,userName);
        task.setIsWished(true);
        task.setRating(task.getRating() + 1);
        taskRepository.updateTask(task);
    }

    public void removeTask(String taskId, String userName) {
        TaskDao task = taskRepository.getTaskById(taskId, userName);
        taskRepository.deleteTask(task);
    }
}

