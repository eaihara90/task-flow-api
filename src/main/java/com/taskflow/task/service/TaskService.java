package com.taskflow.task.service;

import com.taskflow.task.dto.TaskResponseDto;
import com.taskflow.task.dto.TaskStatusRequestDto;
import com.taskflow.task.dto.TaskUpdateRequestDto;
import com.taskflow.task.entity.Task;
import com.taskflow.task.dto.TaskCreateRequestDto;
import com.taskflow.task.entity.TaskStatus;
import com.taskflow.task.exception.InvalidTaskStatusTransitionException;
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
        Task foundTask = getTaskOrThrow(id);
        return taskMapper.convertToTaskResponseDto(foundTask);
    }

    public TaskResponseDto update(Long id, TaskUpdateRequestDto task) {
        Task foundTask = getTaskOrThrow(id);

        foundTask.setTitle(task.title());
        foundTask.setDescription(task.description());

        Task updatedTask = taskRepository.update(foundTask);

        return taskMapper.convertToTaskResponseDto(updatedTask);
    }

    public TaskResponseDto updateStatus(Long id, TaskStatusRequestDto req) {
        Task foundTask = getTaskOrThrow(id);

        if (!validateStatusChange(foundTask.getStatus(), req.status())) {
            throw new InvalidTaskStatusTransitionException(String.format("cannot change task status %s to %s", foundTask.getStatus(), req.status()));
        }

        foundTask.setStatus(req.status());
        Task updatedTask = taskRepository.update(foundTask);

        return taskMapper.convertToTaskResponseDto(updatedTask);
    }

    public void deleteById(Long id) {
        Task foundTask = getTaskOrThrow(id);
        taskRepository.deleteTask(foundTask);
    }

    private boolean validateStatusChange(TaskStatus currentStatus, TaskStatus newStatus) {
        if (currentStatus == newStatus) {
            return true;
        }

        return switch (currentStatus) {
            case PENDING -> newStatus == TaskStatus.IN_PROGRESS;
            case IN_PROGRESS -> newStatus == TaskStatus.COMPLETED;
            case COMPLETED -> false;
        };
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.format("task with id %d not found", id)));
    }
}
