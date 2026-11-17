package com.taskflow.task.dto;

import com.taskflow.task.entity.TaskStatus;

public record TaskResponseDto(Long id, String title, String description, String status) {
}
