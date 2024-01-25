package com.sysaid.assignment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String taskOfTheDayKey;
    private UserSettings userSettings;

    public User(String name) {
        this(name, null, new UserSettings(false));
    }
}
