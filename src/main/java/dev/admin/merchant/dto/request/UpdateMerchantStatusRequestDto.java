package dev.admin.merchant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMerchantStatusRequestDto(
        @Schema(description = "가맹점 상태", example = "true") boolean isDormant
) {}