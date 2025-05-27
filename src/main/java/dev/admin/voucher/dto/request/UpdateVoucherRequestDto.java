package dev.admin.voucher.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateVoucherRequestDto(

        @Schema(description = "요약 설명", example = "커피 50% 할인")
        String description,

        @Schema(description = "상세 설명", example = "전국 모든 가맹점에서 사용 가능")
        String detailDescription,

        @Schema(description = "할인가", example = "5000")
        Integer price,

        @Schema(description = "문의 연락처", example = "스타벅스")
        String contact
) {
}
