package dev.admin.login_log.dto.response;

import dev.admin.login_log.entity.LoginLog;
import dev.admin.login_log.enums.Event;

import java.time.LocalDateTime;

public record LoginLogSimpleResponseDto(
        Long id,
        Long userId,
        Long merchantId,
        Event event,
        Boolean success,
        LocalDateTime timestamp
) {
    public static LoginLogSimpleResponseDto from(LoginLog log) {
        return new LoginLogSimpleResponseDto(
                log.getId(),
                log.getUserId(),
                log.getMerchantId(),
                log.getEvent(),
                log.getSuccess(),
                log.getTimestamp()
        );
    }
}
