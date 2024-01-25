package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    @Value("${external.boredapi.baseURL}")
    private String baseUrl;

    public Task getRandomTask() {

        String endpointUrl = String.format("%s/activity", baseUrl);
        RestTemplate template = new RestTemplate();

        return template.getForEntity(endpointUrl, Task.class).getBody();
    }

    public Task getRandomTaskByType(@RequestParam(name = "type") String type) {

        String endpointUrl = String.format("%s/activity?type=%s", baseUrl, type);
        RestTemplate template = new RestTemplate();

        return template.getForEntity(endpointUrl, Task.class).getBody();
    }
}
