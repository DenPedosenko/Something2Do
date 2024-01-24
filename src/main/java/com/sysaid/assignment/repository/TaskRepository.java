package com.sysaid.assignment.repository;

import com.sysaid.assignment.domain.TaskDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class TaskRepository {
    private final List<byte[]> serializedTasks = new ArrayList<>();

    public void saveTask(TaskDao task) {
        byte[] serializedTask = SerializationUtils.serialize(task);
        serializedTasks.add(serializedTask);
    }

    public List<TaskDao> getAllUsersTasks(String user) {
        List<TaskDao> tasks = new ArrayList<>();
        for (byte[] serializedTask : serializedTasks) {
            TaskDao task = (TaskDao) SerializationUtils.deserialize(serializedTask);
            if (task != null && task.getUser().equals(user)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void updateTask(TaskDao task) {
        for (int i = 0; i < serializedTasks.size(); i++) {
            TaskDao currentTask = (TaskDao) SerializationUtils.deserialize(serializedTasks.get(i));
            if (currentTask != null && currentTask.getUser().equals(task.getUser()) && currentTask.getTask().key().equals(task.getTask().key())) {
                serializedTasks.set(i, SerializationUtils.serialize(task));
            }
        }
    }

    public TaskDao getTaskById(String taskId) {
        for (byte[] serializedTask : serializedTasks) {
            TaskDao task = (TaskDao) SerializationUtils.deserialize(serializedTask);
            if (task != null && task.getTask().key().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    public void deleteTask(TaskDao task) {
        for (Iterator<byte[]> iterator = serializedTasks.iterator(); iterator.hasNext(); ) {

            TaskDao currentTask = (TaskDao) SerializationUtils.deserialize(iterator.next());
            if (currentTask != null && currentTask.getUser().equals(task.getUser()) && currentTask.getTask().key().equals(task.getTask().key())) {
                iterator.remove();
            }
        }
    }
}
