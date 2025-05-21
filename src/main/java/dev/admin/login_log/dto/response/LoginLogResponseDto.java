package dev.admin.login_log.dto.response;

import dev.admin.login_log.entity.LoginLog;
import dev.admin.login_log.enums.Event;

import java.time.LocalDateTime;

public record LoginLogResponseDto(
        Long id,
        Long userId,
        Long merchantId,
        Event event,
        String userAgent,
        LocalDateTime timestamp,
        Boolean success,
        String reason,
        String ipAddress,
        String traceId
) {
    public static LoginLogResponseDto from(LoginLog log) {
        return new LoginLogResponseDto(
                log.getId(),
                log.getUserId(),
                log.getMerchantId(),
                log.getEvent(),
                log.getUserAgent(),
                log.getTimestamp(),
                log.getSuccess(),
                log.getReason(),
                log.getIpAddress(),
                log.getTraceId()
        );
    }
}
