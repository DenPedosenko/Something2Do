package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.model.User;

public interface IUserService {
    void login(String userName);

    boolean isLoggedIn();

    void logout();

    void toggleRatingMode(boolean isRatingEnabled);

    User getCurrentUser();
}
