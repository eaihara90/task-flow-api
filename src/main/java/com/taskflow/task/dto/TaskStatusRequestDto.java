package com.taskflow.task.dto;

import com.taskflow.task.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record TaskStatusRequestDto(

        @NotNull
        TaskStatus status
) {
}
