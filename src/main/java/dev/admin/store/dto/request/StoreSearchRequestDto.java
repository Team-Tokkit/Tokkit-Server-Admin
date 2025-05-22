package dev.admin.store.dto.request;

import dev.admin.store.enums.StoreCategory;
import io.swagger.v3.oas.annotations.media.Schema;

public record StoreSearchRequestDto (

        @Schema(description = "시도 이름", example = "서울특별시")
        String sidoName,

        @Schema(description = "시군구 이름", example = "강남구")
        String sigunguName,

        @Schema(description = "카테고리", example = "FOOD")
        StoreCategory storeCategory,

        @Schema(description = "검색어 (상점명 or 도로명 주소)", example = "스타벅스")
        String keyword
){
}
