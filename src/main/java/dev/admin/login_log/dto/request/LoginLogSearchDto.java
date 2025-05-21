package dev.admin.login_log.dto.request;

import dev.admin.login_log.enums.Event;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginLogSearchDto(
        @Schema(description = "사용자 ID") Long userId,
        @Schema(description = "가맹점주 ID") Long merchantId,
        @Schema(description = "성공 여부") Boolean success,
        @Schema(description = "이벤트 종류") Event event
) {}