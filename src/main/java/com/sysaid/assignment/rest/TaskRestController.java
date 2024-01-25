package com.sysaid.assignment.rest;

import com.sysaid.assignment.domain.model.Task;
import com.sysaid.assignment.domain.model.TaskDao;
import com.sysaid.assignment.domain.model.TaskOfTheDay;
import com.sysaid.assignment.domain.model.User;
import com.sysaid.assignment.domain.request.FetchRequest;
import com.sysaid.assignment.domain.request.TaskOperationRequest;
import com.sysaid.assignment.domain.response.RestResponse;
import com.sysaid.assignment.domain.response.TaskOfTheDayResponse;
import com.sysaid.assignment.domain.response.TaskOperationSuccessResponse;
import com.sysaid.assignment.exception.ToManyActiveTaskException;
import com.sysaid.assignment.mapper.TaskMapper;
import com.sysaid.assignment.service.ITaskService;
import com.sysaid.assignment.service.InternalStorageService;
import com.sysaid.assignment.strategy.TaskOfTheDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskRestController {
    private final InternalStorageService internalStorageService;
    private final ITaskService taskService;
    private final TaskOfTheDayService taskOfTheDayService;

    @GetMapping("/api/v1/tasks/{userName}/wishlist")
    public ResponseEntity<List<Task>> getWishlist(@PathVariable("userName") String userName) {
        User user = internalStorageService.getUserByName(userName);
        List<Task> userTasks = internalStorageService.getAllUsersWishedTasks(user).stream().map(TaskMapper::toDomain).toList();
        return ResponseEntity.ok(userTasks);
    }

    @GetMapping("/api/v1/tasks/{userName}/completed")
    public ResponseEntity<List<Task>> getCompleted(@PathVariable("userName") String userName) {
        User user = internalStorageService.getUserByName(userName);
        List<Task> userTasks = internalStorageService.getAllUsersCompletedTasks(user).stream().map(TaskMapper::toDomain).toList();
        return ResponseEntity.ok(userTasks);
    }

    @PostMapping("/api/v1/tasks/fetch")
    public ResponseEntity<Task> fetchTask(@RequestBody FetchRequest request) {
        List<TaskDao> activeTasks = new ArrayList<>(internalStorageService.getActiveUserTasks(request.getUserName()));
        if (activeTasks.size() < 10) {
            User user = internalStorageService.getUserByName(request.getUserName());
            Task task = taskService.getRandomTaskByType(request.getTaskType());
            internalStorageService.save(TaskMapper.toDao(task, user.getName(), false, false,
                    0));
            return ResponseEntity.ok(task);
        }
        throw new ToManyActiveTaskException();
    }

    @PostMapping("/api/v1/tasks/complete")
    public ResponseEntity<RestResponse> completeTask(@RequestBody TaskOperationRequest request) {
        TaskDao taskDao = internalStorageService.getTaskById(request.getTaskId(), request.getUserName());
        taskDao.setIsCompleted(true);
        internalStorageService.update(taskDao);
        return ResponseEntity.ok(TaskOperationSuccessResponse.builder()
                .task(TaskMapper.toDomain(taskDao))
                .message("Completed")
                .userName(request.getUserName())
                .time(LocalDateTime.now())
                .build());
    }

    @PostMapping("/api/v1/tasks/wished")
    public ResponseEntity<RestResponse> addToWishListTask(@RequestBody TaskOperationRequest request) {
        TaskDao taskDao = internalStorageService.getTaskById(request.getTaskId(), request.getUserName());
        taskDao.setIsWished(true);
        internalStorageService.update(taskDao);
        return ResponseEntity.ok(TaskOperationSuccessResponse.builder()
                .task(TaskMapper.toDomain(taskDao))
                .message("Wished")
                .userName(request.getUserName())
                .time(LocalDateTime.now())
                .build());
    }

    @PostMapping("/api/v1/tasks/remove")
    public ResponseEntity<RestResponse> removeTask(@RequestBody TaskOperationRequest request) {
        TaskDao taskDao = internalStorageService.getTaskById(request.getTaskId(), request.getUserName());
        internalStorageService.removeTask(request.getTaskId(), request.getUserName());
        return ResponseEntity.ok(TaskOperationSuccessResponse.builder()
                .task(TaskMapper.toDomain(taskDao))
                .message("Removed")
                .userName(request.getUserName())
                .time(LocalDateTime.now())
                .build());
    }


    @GetMapping("/api/v1/tasks/{userName}/taskOfTheDay")
    public ResponseEntity<RestResponse> getTaskOfTheDay(@PathVariable("userName") String userName) {
        User user = internalStorageService.getUserByName(userName);
        if (taskOfTheDayService.isCurrentTaskOfTheDayExpired(user)) {
            taskOfTheDayService.getTaskOfTheDayStrategy(user).setTaskOfTheDay();
        }
        TaskOfTheDay taskOfTheDay = (TaskOfTheDay) internalStorageService.getTaskById(user.getTaskOfTheDayKey(), user.getName());
        return ResponseEntity.ok(TaskOfTheDayResponse.builder()
                .task(TaskMapper.toDomain(taskOfTheDay))
                .userName(userName)
                .isCompleted(taskOfTheDay.getIsCompleted())
                .fetchTime(taskOfTheDay.getFetchTime())
                .remainingTime(taskOfTheDay.getRemainingTime())
                .build());
    }
}
