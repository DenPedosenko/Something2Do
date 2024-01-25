package com.sysaid.assignment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserSettings implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private boolean isRatingEnabled;
}
