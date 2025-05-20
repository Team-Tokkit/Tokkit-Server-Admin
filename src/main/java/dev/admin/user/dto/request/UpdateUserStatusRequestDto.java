package dev.admin.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserStatusRequestDto(
        @Schema(description = "사용자 상태", example = "true") boolean isDormant
) {}
