package com.sysaid.assignment.controller;

import com.sysaid.assignment.domain.model.Task;
import com.sysaid.assignment.domain.model.TaskDao;
import com.sysaid.assignment.mapper.TaskMapper;
import com.sysaid.assignment.service.InternalStorageService;
import com.sysaid.assignment.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


/**
 * Task Controller class.
 * Handles all requests related to tasks.
 * The class is annotated with @Controller to indicate that it is a Spring MVC controller.
 * The class is annotated with @RequiredArgsConstructor to enable constructor-based dependency injection.
 * The class contains the following methods:
 * - getUncompletedTasks: Retrieves uncompleted tasks for a specified user and task type.
 * - markCompleted: Marks a task as completed based on the provided task ID.
 * - markWished: Marks a task as favorite (wished) based on the provided task ID.
 * - removeTask: Removes a task based on the provided task ID.
 * The class contains the following fields:
 * - internalStorageService: An instance of the InternalStorageService class.
 * - taskService: An instance of the TaskServiceImpl class.
 */
@Controller
@RequiredArgsConstructor
public class TaskController {
    private final InternalStorageService internalStorageService;
    private final TaskServiceImpl taskService;

    /**
     * Retrieves uncompleted tasks for a specified user and task type.
     *
     * @param user  The username for which tasks are retrieved.
     * @param type  The type of tasks to retrieve.
     * @param model The Spring MVC model for adding attributes.
     * @return A view name indicating the result of the operation.
     * If successful, redirects to the main page with the updated tasks.
     * If unsuccessful (e.g., insufficient tasks), redirects to the main page with an error indicator.
     */
    @PostMapping("/retrieveTasks/{user}")
    public String getUncompletedTasks(@PathVariable("user") String user, @RequestParam(name = "taskType") String type, Model model) {
        List<TaskDao> activeTasks = new ArrayList<>(internalStorageService.getActiveUserTasks(user));
        if (activeTasks.size() < 10) {
            Task randomTask = taskService.getRandomTaskByType(type);
            TaskDao task = TaskMapper.toDao(randomTask, user, false, false, 0);
            internalStorageService.save(task);
            activeTasks.add(task);
            model.addAttribute("tasks", activeTasks);
            return "redirect:/main";
        }
        return "redirect:/main?error=true";
    }

    /**
     * Marks a task as completed based on the provided task ID.
     *
     * @param taskId The unique identifier of the task to be marked as completed.
     * @return A view name indicating the result of the operation.
     * Redirects to the main page after marking the task as completed.
     */
    @PostMapping("/markCompleted")
    public String markCompleted(@RequestParam String taskId, @RequestParam String userName) {
        internalStorageService.markTaskAsCompleted(taskId, userName);
        return "redirect:/main";
    }

    /**
     * Marks a task as favorite (wished) based on the provided task ID.
     *
     * @param taskId The unique identifier of the task to be marked as favorite.
     * @return A view name indicating the result of the operation.
     * Redirects to the main page after marking the task as favorite.
     */
    @PostMapping("/markFavorite")
    public String markWished(@RequestParam String taskId, @RequestParam String userName) {
        internalStorageService.markTaskAsWished(taskId, userName);
        return "redirect:/main";
    }

    /**
     * Removes a task based on the provided task ID.
     *
     * @param taskId The unique identifier of the task to be removed.
     * @return A view name indicating the result of the operation.
     * Redirects to the main page after removing the task.
     */
    @PostMapping("/removeTask")
    public String removeTask(@RequestParam String taskId, @RequestParam String userName) {
        internalStorageService.removeTask(taskId, userName);
        return "redirect:/main";
    }
}
