package com.sysaid.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class TaskDao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Task task;
    private String user;
    private Boolean isCompleted;
    private Boolean isWished;
    private int rating;
}
