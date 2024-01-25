package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.model.User;

import java.util.List;

public interface IUserStorageService extends DataStorage {
    List<User> getAllUsers();
    User getUserByName(String userName);

}
