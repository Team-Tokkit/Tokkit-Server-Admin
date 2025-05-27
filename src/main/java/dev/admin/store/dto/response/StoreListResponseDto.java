package dev.admin.store.dto.response;

import dev.admin.store.entity.Store;
import dev.admin.store.enums.StoreCategory;
import io.swagger.v3.oas.annotations.media.Schema;

public record StoreListResponseDto(

        @Schema(description = "Store ID", example = "1")
        Long id,

        @Schema(description = "상점 이름", example = "스타벅스 강남점")
        String storeName,

        @Schema(description = "도로명 주소", example = "서울 강남구 테헤란로 123")
        String roadAddress,

        @Schema(description = "카테고리", example = "FOOD")
        StoreCategory storeCategory

//        @Schema(description = "가맹점주 이름", example = "김가맹")
//        String merchantName,
//
//        @Schema(description = "가맹점주 연락처", example = "010-1111-2222")
//        String merchantPhone

) {
    public static StoreListResponseDto from(Store store) {
        return new StoreListResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getRoadAddress(),
                store.getStoreCategory()
//                store.getMerchant().getName(),
//                store.getMerchant().getPhoneNumber()
        );
    }
}