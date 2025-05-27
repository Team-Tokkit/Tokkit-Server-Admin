package dev.admin.voucher.dto.request;

import dev.admin.global.entity.VoucherImage;
import dev.admin.store.enums.StoreCategory;
import dev.admin.voucher.entity.Voucher;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record CreateVoucherRequestDto(

        @Schema(description = "바우처 이름", example = "카페 할인권")
        String name,

        @Schema(description = "요약 설명", example = "커피 50% 할인")
        String description,

        @Schema(description = "상세 설명", example = "전국 모든 가맹점에서 사용 가능")
        String detailDescription,

        @Schema(description = "정가", example = "10000")
        Integer originalPrice,

        @Schema(description = "할인가", example = "5000")
        Integer price,

        @Schema(description = "총 수량", example = "100")
        Integer totalCount,

        @Schema(description = "사용 유효일", example = "2025-12-31T23:59:59")
        LocalDateTime validDate,

        @Schema(description = "환불 정책", example = "사용 전 언제든 환불 가능")
        String refundPolicy,

        @Schema(description = "문의 연락처", example = "010-1234-5678")
        String contact,

        @Schema(description = "카테고리", example = "FOOD")
        StoreCategory storeCategory,

        @Schema(description = "사용처 store ID 목록", example = "[1, 2, 3]")
        List<Long> storeIds,

        @Schema(description = "이미지 URL 또는 S3 파일 이름", example = "images/cafe_coupon_01.png")
        String imageUrl

) {
    public Voucher toEntity() {
        return Voucher.builder()
                .name(this.name)
                .description(this.description)
                .detailDescription(this.detailDescription)
                .price(this.price)
                .originalPrice(this.originalPrice)
                .totalCount(this.totalCount)
                .remainingCount(this.totalCount)
                .validDate(this.validDate)
                .refundPolicy(this.refundPolicy)
                .contact(this.contact)
                .storeCategory(this.storeCategory)
                .build();
    }

    public VoucherImage toVoucherImage(Voucher voucher) {
        return VoucherImage.builder()
                .voucher(voucher)
                .imageUrl(this.imageUrl)
                .build();
    }
}
