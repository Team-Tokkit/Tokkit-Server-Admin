package dev.admin.voucher.dto.response;

import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.voucher.entity.Voucher;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record VoucherResponseDto(

        @Schema(description = "바우처 ID", example = "1")
        Long id,

        @Schema(description = "바우처 이름", example = "카페 할인권")
        String name,

        @Schema(description = "할인가", example = "5000")
        Integer price,

        @Schema(description = "정가", example = "10000")
        Integer originalPrice,

        @Schema(description = "총 수량", example = "100")
        Integer totalCount,

        @Schema(description = "남은 수량", example = "35")
        Integer remainingCount,

        @Schema(description = "유효기간", example = "2025-12-31T23:59:59")
        LocalDateTime validDate,

        @Schema(description = "상세 설명", example = "전국 카페에서 사용 가능한 할인권입니다.")
        String detailDescription,

        @Schema(description = "환불 정책", example = "사용 전 언제든지 환불 가능합니다.")
        String refundPolicy,

        @Schema(description = "문의 연락처", example = "카페24 고객센터")
        String contact,

        @Schema(description = "이미지 전체 경로", example = "https://cdn.example.com/images/cafe_coupon.png")
        String imageUrl,

        @Schema(description = "연결된 스토어 목록 (페이지네이션 포함)")
        Page<StoreListResponseDto> stores

) {
    public static VoucherResponseDto from(Voucher voucher, Page<StoreListResponseDto> stores, String imageProxyBaseUrl) {
        String imageUrl = null;
        if (voucher.getImage() != null && voucher.getImage().getImageUrl() != null) {
            imageUrl = imageProxyBaseUrl + voucher.getImage().getImageUrl();
        }

        return new VoucherResponseDto(
                voucher.getId(),
                voucher.getName(),
                voucher.getPrice(),
                voucher.getOriginalPrice(),
                voucher.getTotalCount(),
                voucher.getRemainingCount(),
                voucher.getValidDate(),
                voucher.getDetailDescription(),
                voucher.getRefundPolicy(),
                voucher.getContact(),
                imageUrl,
                stores
        );
    }
}
