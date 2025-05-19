package dev.admin.api_request_log.dto.response;

import java.time.LocalDateTime;

public record ApiRequestLogPageItemDto(
        Long id,
        String endpoint,
        String method,
        int status,
        int responseTime,
        Long userId,
        LocalDateTime timestamp
) {}
