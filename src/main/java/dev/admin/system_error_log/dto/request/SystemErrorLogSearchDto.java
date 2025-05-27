package dev.admin.system_error_log.dto.request;

import dev.admin.system_error_log.enums.Severity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record SystemErrorLogSearchDto(
        String keyword,
        Severity severity
) {}
