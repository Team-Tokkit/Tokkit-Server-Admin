package dev.admin.merchant.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MerchantSimpleResponseDto(
        @Schema(description = "가맹점 ID", example = "101") Long id,
        @Schema(description = "가맹점주명", example = "Merchant 1") String name,
        @Schema(description = "이메일", example = "user1@mail.com")String email,
        @Schema(description = "전화 번호", example = "010-1234-5678") String phoneNumber,
        @Schema(description = "상태", example = "true")  boolean isDormant,
        @Schema(description = "가입일", example = "2025-05-20") LocalDateTime createdAt
) {
    public static MerchantSimpleResponseDto of(
            Long id,
            String name,
            String email,
            String phoneNumber,
            boolean isDormant,
            LocalDateTime createdAt
    ) {
        return new MerchantSimpleResponseDto(id, name, email, phoneNumber, isDormant, createdAt);
    }
}