package com.taskflow.task.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public record ApiErrorResponseDto (
        int status,
        String message,
        Date timestamp,
        Map<String, List<String>> errors
) {
}
