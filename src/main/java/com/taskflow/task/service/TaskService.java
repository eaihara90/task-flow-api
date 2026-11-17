package com.taskflow.task.service;

import com.taskflow.task.dto.TaskResponseDto;
import com.taskflow.task.entity.Task;
import com.taskflow.task.dto.TaskCreateRequestDto;
import com.taskflow.task.mapper.TaskMapper;
import com.taskflow.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDto save(TaskCreateRequestDto task) {
        if (task.title() == null || task.title().isBlank()) {
            throw new IllegalArgumentException("title cannot be empty");
        }
        if (task.description() == null || task.description().isBlank()) {
            throw new IllegalArgumentException("description cannot be empty");
        }

        Task newTask = taskMapper.convertDtoToTask(task);
        Task response = taskRepository.save(newTask);

        return taskMapper.convertToTaskResponseDto(response);
    }

    public List<TaskResponseDto> findAll() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().map(taskMapper::convertToTaskResponseDto).toList();
    }

    public TaskResponseDto findById(Long id) {
        Optional<Task> foundTask = taskRepository.findById(id);

        if (foundTask.isEmpty()) {
            throw new IllegalArgumentException("Task with id " + id + " does not exist");
        }

        return taskMapper.convertToTaskResponseDto(foundTask.get());
    }
}
