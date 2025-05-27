package dev.admin.merchant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMerchantRequestDto(
        @Schema(description = "가맹점주명", example = "김가맹씨") String name,
        @Schema(description = "연락처", example = "011-1243-4567") String phoneNumber,
        @Schema(description = "간편 비밀번호", example = "000000") String simplePassword
) {

}
