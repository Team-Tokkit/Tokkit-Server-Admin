package dev.admin.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserRequestDto(
        @Schema(description = "사용자 이름", example = "홍길동") String name,
        @Schema(description = "전화번호", example = "010-1234-5678") String phoneNumber,
        @Schema(description = "간편 비밀번호", example = "000000") String simplePassword
) {}
