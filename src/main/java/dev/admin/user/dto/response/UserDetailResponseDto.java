package dev.admin.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDetailResponseDto(
        @Schema(description = "사용자 ID", example = "1") Long id,
        @Schema(description = "이름", example = "홍길동") String name,
        @Schema(description = "이메일", example = "hong@example.com") String email,
        @Schema(description = "전화번호", example = "010-1234-5678") String phoneNumber,
        @Schema(description = "상태", example = "true") boolean isDormant,
        @Schema(description = "가입일", example = "2025-05-20") String createdAt,
        @Schema(description = "지갑 id", example = "1") long walletId
) {
    public static UserDetailResponseDto of(
            Long id,
            String name,
            String email,
            String phoneNumber,
            boolean status,
            String createdAt,
            long walletId
    ) {
        return new UserDetailResponseDto(id, name, email, phoneNumber, status, createdAt, walletId);
    }
}
