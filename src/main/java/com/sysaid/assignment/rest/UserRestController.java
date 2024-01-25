package com.sysaid.assignment.rest;

import com.sysaid.assignment.domain.model.User;
import com.sysaid.assignment.service.InternalStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserRestController {
    private final InternalStorageService internalStorageService;


    @PostMapping("/api/v1/users/create")
    public ResponseEntity<User> createUser(@RequestParam String userName) {
        User user = new User(userName);
        internalStorageService.save(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/api/v1/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(internalStorageService.getAllUsers());
    }

    @PostMapping("/api/v1/{userId}/ratingMode")
    public ResponseEntity<User> setRatingMode(@PathVariable String userId, @RequestParam boolean isRatingEnabled) {
        User user = internalStorageService.getUserByName(userId);
        user.getUserSettings().setRatingEnabled(isRatingEnabled);
        internalStorageService.update(user);
        return ResponseEntity.ok(user);
    }

}
