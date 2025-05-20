package dev.admin.merchant.dto.response;


import dev.admin.merchant.entity.Merchant;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MerchantDetailResponseDto(
        @Schema(description = "가맹점 ID", example = "101") Long id,
        @Schema(description = "가맹점주명", example = "Merchant 1") String name,
        @Schema(description = "전화 번호", example = "010-1234-5678") String phoneNumber,
        @Schema(description = "이메일", example = "user1@mail.com") String email,
        @Schema(description = "사업자 등록 번호", example = "123-45-67890") String businessNumber,
        @Schema(description = "상태", example = "true") boolean isDormant,
        @Schema(description = "가입일", example = "2025-05-20") LocalDateTime createdAt,
        @Schema(description = "지갑ID", example = "1") long walletId
) {
    public static MerchantDetailResponseDto from(
            Merchant merchant
    ) {
        return new MerchantDetailResponseDto(
                merchant.getId(),
                merchant.getName(),
                merchant.getPhoneNumber(),
                merchant.getEmail(),
                merchant.getBusinessNumber(),
                merchant.getIsDormant(),
                merchant.getCreatedAt(),
                merchant.getWallet().getId()
        );
    }
}