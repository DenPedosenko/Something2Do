package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.model.User;
import com.sysaid.assignment.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Getter
    private String currentUserName;

    public void login(String userName) {
        User user = userRepository.getUserByName(userName);
        if (user == null) {
            user = new User(userName);
            userRepository.saveUser(user);
        }
        currentUserName = userName;
    }

    public boolean isLoggedIn() {
        return currentUserName != null;
    }

    public void logout() {
        if (isLoggedIn()) {
            currentUserName = null;
        }
    }

    public void toggleRatingMode(boolean isRatingEnabled) {
        User user = getCurrentUser();
        user.getUserSettings().setRatingEnabled(isRatingEnabled);
        userRepository.updateUser(user);
    }

    public User getCurrentUser() {
        return userRepository.getUserByName(this.currentUserName);
    }
}
