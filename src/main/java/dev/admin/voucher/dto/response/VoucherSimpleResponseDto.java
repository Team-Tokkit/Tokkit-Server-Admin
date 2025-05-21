package dev.admin.voucher.dto.response;

import dev.admin.store.enums.StoreCategory;
import dev.admin.voucher.entity.Voucher;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record VoucherSimpleResponseDto(

        @Schema(description = "바우처 ID", example = "1")
        Long id,

        @Schema(description = "바우처 이미지 URL", example = "images/cafe_coupon_01.png")
        String imageUrl,

        @Schema(description = "바우처 이름", example = "카페 할인권")
        String name,

        @Schema(description = "정가", example = "10000")
        Integer originalPrice,

        @Schema(description = "할인 금액", example = "5000")
        Integer price,

        @Schema(description = "총 수량", example = "100")
        Integer totalCount,

        @Schema(description = "남은 수량", example = "47")
        Integer remainingCount,

        @Schema(description = "유효기간", example = "2025-12-31T23:59:59")
        LocalDateTime validDate,

        @Schema(description = "발급처", example = "카페24")
        String contact,

        @Schema(description = "스토어 카테고리", example = "FOOD")
        StoreCategory storeCategory
) {
    public static VoucherSimpleResponseDto from(Voucher voucher, String imageProxyBaseUrl) {
        String imageUrl = null;
        if (voucher.getImage() != null && voucher.getImage().getImageUrl() != null) {
            imageUrl = imageProxyBaseUrl + voucher.getImage().getImageUrl();
        }

        return new VoucherSimpleResponseDto(
                voucher.getId(),
                imageUrl,
                voucher.getName(),
                voucher.getOriginalPrice(),
                voucher.getPrice(),
                voucher.getTotalCount(),
                voucher.getRemainingCount(),
                voucher.getValidDate(),
                voucher.getContact(),
                voucher.getStoreCategory()
        );
    }
}
