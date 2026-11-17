package com.taskflow.task.controller;

import com.taskflow.task.dto.TaskCreateRequestDto;
import com.taskflow.task.dto.TaskResponseDto;
import com.taskflow.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> save(@RequestBody TaskCreateRequestDto task) {
        TaskResponseDto response = taskService.save(task);
        return ResponseEntity.created(URI.create("/tasks/" + response.id().toString())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> listAll() {
        List<TaskResponseDto> response = taskService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findById(@PathVariable Long id) {

        TaskResponseDto response = taskService.findById(id);
        return ResponseEntity.ok().body(response);
    }
}
