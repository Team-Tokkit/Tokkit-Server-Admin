package dev.admin.store.controller;


import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.store.dto.request.StoreSearchRequestDto;
import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.store.service.query.StoreQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/vouchers")
public class StoreController {

    private final StoreQueryService storeQueryService;

    @GetMapping("/stores")
    @Operation(summary = "상점 목록 조회 및 검색", description = "시도/시군구/카테고리/검색어로 상점 목록을 조회합니다.")
    public ApiResponse<Page<StoreListResponseDto>> searchStores(@ModelAttribute StoreSearchRequestDto request, Pageable pageable) {
        log.info("sidoName={}, sigunguName={}, category={}, keyword={}",
                request.sidoName(), request.sigunguName(), request.storeCategory(), request.keyword());
        return ApiResponse.onSuccess(storeQueryService.searchStores(request, pageable));
    }
}
