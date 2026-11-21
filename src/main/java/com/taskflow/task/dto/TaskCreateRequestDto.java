package com.taskflow.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequestDto(

        @Size(min = 3, max = 100, message = "o titulo deve ter entre 3 e 100 caracteres")
        @NotBlank(message = "nao pode ser branco")
        String title,

        @NotBlank
        @Size(min = 1, max = 500)
        String description) {
}
