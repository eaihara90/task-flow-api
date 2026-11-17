package com.taskflow.task.mapper;

import com.taskflow.task.dto.TaskCreateRequestDto;
import com.taskflow.task.dto.TaskResponseDto;
import com.taskflow.task.entity.Task;
import com.taskflow.task.entity.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskMapper {
    public Task convertDtoToTask(TaskCreateRequestDto taskCreateRequestDto) {
        Task task = new Task();
        task.setTitle(taskCreateRequestDto.title().trim());
        task.setDescription(taskCreateRequestDto.description().trim());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(new Date());

        return task;
    }

    public TaskResponseDto convertToTaskResponseDto(Task task) {
        String status = this.statusEnumToString(task.getStatus());
        TaskResponseDto taskResponseDto = new TaskResponseDto(task.getId(), task.getTitle(), task.getDescription(), status);
        return taskResponseDto;
    }

    private String statusEnumToString(TaskStatus taskStatus) {
        return switch (taskStatus) {
            case PENDING -> "pending";
            case COMPLETED -> "completed";
            case IN_PROGRESS -> "in progress";
        };
    }
}
