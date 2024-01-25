package com.sysaid.assignment.domain.response;

import com.sysaid.assignment.domain.model.Task;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
public class TaskOfTheDayResponse extends RestResponse {
    String userName;
    Task task;
    Boolean isCompleted;
    LocalDateTime fetchTime;
    LocalDateTime remainingTime;

    @Builder
    public TaskOfTheDayResponse(String userName, Task task, Boolean isCompleted, LocalDateTime fetchTime, LocalDateTime remainingTime) {
        this.userName = userName;
        this.task = task;
        this.isCompleted = isCompleted;
        this.fetchTime = fetchTime;
        this.remainingTime = remainingTime;
    }
}
