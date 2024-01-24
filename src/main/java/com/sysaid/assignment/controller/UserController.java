package com.sysaid.assignment.controller;

import com.sysaid.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSuccess(@RequestParam String username) {
        userService.login(username);
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/login";
    }

    @PostMapping("/toggleRatingMode")
    public String toggleRatingMode(@RequestParam(name = "isRatingEnabled") boolean isRatingEnabled) {
        userService.toggleRatingMode(!isRatingEnabled);
        return "redirect:/main";
    }
}
