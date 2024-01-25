package com.sysaid.assignment.repository;

import com.sysaid.assignment.domain.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<byte[]> serializedUsers = new ArrayList<>();

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (byte[] serializedUser : serializedUsers) {
            User user = (User) SerializationUtils.deserialize(serializedUser);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public void saveUser(User user) {
        validateUser(user);
        if (getUserByName(user.getName()) != null) {
            throw new IllegalArgumentException("User with name " + user.getName() + " already exists");
        }

        byte[] serializedUser = SerializationUtils.serialize(user);
        serializedUsers.add(serializedUser);
    }

    public void updateUser(User user) {
        validateUser(user);
        for (int i = 0; i < serializedUsers.size(); i++) {
            User currentUser = (User) SerializationUtils.deserialize(serializedUsers.get(i));
            if (currentUser != null && currentUser.getName().equals(user.getName())) {
                serializedUsers.set(i, SerializationUtils.serialize(user));
            }
        }
    }


    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getName() == null) {
            throw new IllegalArgumentException("User's username cannot be null");
        }
    }

    public User getUserByName(String userName) {
        for (byte[] serializedUser : serializedUsers) {
            User user = (User) SerializationUtils.deserialize(serializedUser);
            if (user != null && user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
