package com.sysaid.assignment.controller;

import com.sysaid.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class handling user-related endpoints and actions.
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    /**
     * Service for handling user-related operations.
     */
    private final UserService userService;

    /**
     * Display the login form.
     *
     * @return The login form template name.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Process a successful login and redirect to the main page.
     *
     * @param username The username provided in the login form.
     * @return Redirect to the main page.
     */
    @PostMapping("/login")
    public String loginSuccess(@RequestParam String username) {
        userService.login(username);
        return "redirect:/main";
    }

    /**
     * Logout the current user and redirect to the login page.
     *
     * @return Redirect to the login page.
     */
    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/login";
    }

    /**
     * Toggle the rating mode for the current user.
     *
     * @param isRatingEnabled The current rating mode status.
     * @return Redirect to the main page after toggling the rating mode.
     */
    @PostMapping("/toggleRatingMode")
    public String toggleRatingMode(@RequestParam(name = "isRatingEnabled") boolean isRatingEnabled) {
        userService.toggleRatingMode(!isRatingEnabled);
        return "redirect:/main";
    }
}
