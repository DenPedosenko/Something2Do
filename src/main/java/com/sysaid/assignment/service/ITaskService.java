package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.model.Task;

public interface ITaskService {
    Task getRandomTask();

    Task getRandomTaskByType(String type);
}
