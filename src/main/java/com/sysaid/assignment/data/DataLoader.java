package com.sysaid.assignment.data;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.domain.TaskDao;
import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.domain.UserSettings;
import com.sysaid.assignment.service.InternalStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class DataLoader implements ApplicationRunner {

    private final InternalStorageService internalStorageService;

    @Autowired
    public DataLoader(InternalStorageService internalStorageService) {
        this.internalStorageService = internalStorageService;
    }

    private void loadTasksFromYaml() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = DataLoader.class.getResourceAsStream("/testData.yml")) {
            if (inputStream != null) {
                Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
                List<Map<String, Object>> taskDataList = data.get("tasks");

                List<TaskDao> tasks = new ArrayList<>();
                for (Map<String, Object> taskData : taskDataList) {
                    TaskDao taskDao = new TaskDao(createTask(taskData.get("task")), taskData.get("user").toString(), (Boolean) taskData.get("isCompleted"), (Boolean) taskData.get("isWished"), (Integer) taskData.get("rating"));
                    internalStorageService.save(taskDao);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading tasks from yaml file");
        }
    }

    private Task createTask(Object object) {
        try {
            Map<String, Object> task = (Map<String, Object>) object;
            return new Task(task.get("activity").toString(), Float.parseFloat(task.get("accessibility").toString()), task.get("type").toString(), Integer.parseInt(task.get("participants").toString()), Float.parseFloat(task.get("price").toString()), task.get("link").toString(), task.get("key").toString());

        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Task data is not valid");
        }
    }

    private void loadUsersFromYaml() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = DataLoader.class.getResourceAsStream("/testData.yml")) {
            if (inputStream != null) {
                Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
                List<Map<String, Object>> usersDataList = data.get("users");

                List<User> users = new ArrayList<>();
                for (Map<String, Object> taskData : usersDataList) {
                    User user = new User(taskData.get("name").toString(), null, createUserSettings(taskData.get("userSettings")));
                    internalStorageService.save(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading users from yaml file");
        }
    }

    private UserSettings createUserSettings(Object userSettings) {
        try {
            Map<String, Object> settings = (Map<String, Object>) userSettings;
            return new UserSettings((Boolean) settings.get("isRatingEnabled"));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("User settings data is not valid");
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadTasksFromYaml();
        loadUsersFromYaml();
    }
}
