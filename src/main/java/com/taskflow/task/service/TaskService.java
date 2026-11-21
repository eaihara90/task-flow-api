package com.taskflow.task.service;

import com.taskflow.task.dto.TaskResponseDto;
import com.taskflow.task.entity.Task;
import com.taskflow.task.dto.TaskCreateRequestDto;
import com.taskflow.task.exception.TaskNotFoundException;
import com.taskflow.task.mapper.TaskMapper;
import com.taskflow.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Task newTask = taskMapper.convertDtoToTask(task);
        Task response = taskRepository.save(newTask);

        return taskMapper.convertToTaskResponseDto(response);
    }

    public List<TaskResponseDto> findAll() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().map(taskMapper::convertToTaskResponseDto).toList();
    }

    public TaskResponseDto findById(Long id) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(String.format("task with id %d not found", id))
                );
        return taskMapper.convertToTaskResponseDto(foundTask);
    }
}
