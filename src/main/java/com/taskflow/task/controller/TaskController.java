package com.taskflow.task.controller;

import com.taskflow.task.dto.TaskCreateRequestDto;
import com.taskflow.task.dto.TaskResponseDto;
import com.taskflow.task.dto.TaskStatusRequestDto;
import com.taskflow.task.dto.TaskUpdateRequestDto;
import com.taskflow.task.service.TaskService;
import jakarta.validation.Valid;
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
    public ResponseEntity<TaskResponseDto> save(@Valid @RequestBody TaskCreateRequestDto task) {
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

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequestDto task) {
        TaskResponseDto response = taskService.update(id, task);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> updateStatus(@PathVariable Long id, @Valid @RequestBody TaskStatusRequestDto task) {
        TaskResponseDto response = taskService.updateStatus(id, task);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
