package com.taskflow.task.repository;

import com.taskflow.task.dto.TaskCreateRequestDto;
import com.taskflow.task.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task save(Task task);
}
