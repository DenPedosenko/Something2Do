package com.sysaid.assignment.controller;

import com.sysaid.assignment.domain.model.TaskDao;
import com.sysaid.assignment.domain.model.TaskOfTheDay;
import com.sysaid.assignment.domain.model.User;
import com.sysaid.assignment.service.InternalStorageService;
import com.sysaid.assignment.service.UserService;
import com.sysaid.assignment.strategy.TaskOfTheDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Main Controller class.
 * Handles all requests related to the main page.
 * The class is annotated with @Controller to indicate that it is a Spring MVC controller.
 * The class is annotated with @RequiredArgsConstructor to enable constructor-based dependency injection.
 * The class contains the following methods:
 * - showMainPage: Shows the main page.
 * - showWishlist: Shows the wishlist page.
 * - showCompleted: Shows the completed tasks page.
 * The class contains the following fields:
 * - userService: An instance of the UserService class.
 * - internalStorageService: An instance of the InternalStorageService class.
 * - taskOfTheDayService: An instance of the TaskOfTheDayService class.
 * - TASK_TYPES: A list of task types.
 */
@Controller
@RequiredArgsConstructor
public class MainController {
    private static final List<String> TASK_TYPES = List.of("education", "recreational", "social", "diy", "charity",
            "cooking", "relaxation", "music", "busywork");
    private final UserService userService;
    private final InternalStorageService internalStorageService;
    private final TaskOfTheDayService taskOfTheDayService;

    /**
     * Shows the main page.
     *
     * @param model The Spring MVC model for adding attributes.
     * @return A view name indicating the result of the operation.
     * If successful, redirects to the main page.
     */
    @GetMapping("/main")
    public String showMainPage(Model model) {
        if (userService.isLoggedIn()) {
            User user = userService.getCurrentUser();
            if (taskOfTheDayService.isCurrentTaskOfTheDayExpired(user)) {
                taskOfTheDayService.getTaskOfTheDayStrategy(user).setTaskOfTheDay();
            }
            List<TaskDao> userTasks = internalStorageService.getActiveUserTasks(user.getName());
            TaskOfTheDay taskOfTheDay = (TaskOfTheDay) internalStorageService.getTaskById(user.getTaskOfTheDayKey(), user.getName());
            model.addAttribute("username", userService.getCurrentUserName());
            model.addAttribute("isRatingEnabled", user.getUserSettings().isRatingEnabled());
            model.addAttribute("taskOfTheDay", taskOfTheDay);
            model.addAttribute("types", TASK_TYPES);
            model.addAttribute("tasks", userTasks);
            return "main";
        }
        return "redirect:/login";
    }

    /**
     * Shows the wishlist page.
     * @param model The Spring MVC model for adding attributes.
     * @return A view name indicating the result of the operation.
     * If successful, redirects to the wishlist page with the wished tasks.
     */
    @GetMapping("/wishlist")
    public String showWishlist(Model model) {
        if (userService.isLoggedIn()) {
            User user = userService.getCurrentUser();
            model.addAttribute("username", userService.getCurrentUserName());
            List<TaskDao> userTasks = internalStorageService.getAllUsersWishedTasks(user);
            model.addAttribute("tasks", userTasks);
            return "wishlist";
        }
        return "redirect:/login";
    }


    /**
     * Shows the completed tasks page.
     * @param model The Spring MVC model for adding attributes.
     * @param page The current page number.
     * @return A view name indicating the result of the operation.
     * If successful, redirects to the completed tasks page with the completed tasks.
     */
    @GetMapping("/completed")
    public String showCompleted(Model model, @RequestParam(defaultValue = "0") int page) {
        if (userService.isLoggedIn()) {
            User user = userService.getCurrentUser();
            model.addAttribute("username", userService.getCurrentUserName());
            List<TaskDao> completedTasks = internalStorageService.getAllUsersCompletedTasks(user);
            int pageSize = 10;
            int totalPages = getTotalPages(completedTasks.size(), pageSize);
            if (page < 0 || page > totalPages) {
                return "redirect:/completed?page=0";
            }
            int startIndex = page * pageSize;
            int endIndex = Math.min(startIndex + pageSize, completedTasks.size());

            List<TaskDao> pagedCompletedTasks = completedTasks.subList(startIndex, endIndex);

            model.addAttribute("pagedCompletedTasks", pagedCompletedTasks);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", getTotalPages(completedTasks.size(), pageSize));

            return "completed";
        }
        return "redirect:/login";
    }

    private int getTotalPages(int totalTasks, int pageSize) {
        return (int) Math.ceil((double) totalTasks / pageSize);
    }
}
