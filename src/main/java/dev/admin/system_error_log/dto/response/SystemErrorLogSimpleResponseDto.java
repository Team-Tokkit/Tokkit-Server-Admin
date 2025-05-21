package dev.admin.system_error_log.dto.response;

import dev.admin.system_error_log.entity.SystemErrorLog;
import dev.admin.system_error_log.enums.Severity;

import java.time.LocalDateTime;

public record SystemErrorLogSimpleResponseDto(
        Long id,
        String endpoint,
        String traceId,
        Severity severity,
        LocalDateTime timestamp
) {
    public static SystemErrorLogSimpleResponseDto from(SystemErrorLog log) {
        return new SystemErrorLogSimpleResponseDto(
                log.getId(),
                log.getEndpoint(),
                log.getTraceId(),
                log.getSeverity(),
                log.getTimestamp()
        );
    }
}
