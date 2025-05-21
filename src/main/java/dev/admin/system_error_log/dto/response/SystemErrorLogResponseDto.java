package dev.admin.system_error_log.dto.response;

import dev.admin.system_error_log.entity.SystemErrorLog;
import dev.admin.system_error_log.enums.Severity;

import java.time.LocalDateTime;

public record SystemErrorLogResponseDto(
        Long id,
        Long userId,
        String endpoint,
        String errorMessage,
        String stackTrace,
        LocalDateTime timestamp,
        String serverName,
        String traceId,
        Severity severity
) {
    public static SystemErrorLogResponseDto from(SystemErrorLog log) {
        return new SystemErrorLogResponseDto(
                log.getId(),
                log.getUserId(),
                log.getEndpoint(),
                log.getErrorMessage(),
                log.getStackTrace(),
                log.getTimestamp(),
                log.getServerName(),
                log.getTraceId(),
                log.getSeverity()
        );
    }
}
