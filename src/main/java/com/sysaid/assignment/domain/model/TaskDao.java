package com.sysaid.assignment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor
public class TaskDao implements Serializable, Comparable<TaskDao> {

    @Serial
    private static final long serialVersionUID = 1L;
    private Task task;
    private String user;
    private Boolean isCompleted;
    private Boolean isWished;
    private int rating;

    @Override
    public int compareTo(TaskDao other) {
        return Double.compare(this.getRating(), other.getRating());
    }
}
