package com.sysaid.assignment.domain.response;

import com.sysaid.assignment.domain.model.Task;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskOperationSuccessResponse extends RestResponse {
    private Task task;
    private String message;

    @Builder
    public TaskOperationSuccessResponse(String userName, LocalDateTime time, Task task, String message) {
        super(userName, time);
        this.task = task;
        this.message = message;
    }
}


