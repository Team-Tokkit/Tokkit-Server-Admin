package dev.admin.store.controller;


import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.store.service.query.StoreQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/vouchers")
public class StoreController {

    private final StoreQueryService storeQueryService;

    @GetMapping("/stores")
    @Operation(summary = "상점 목록 조회", description = "상점 목록을 조회합니다.")
    public ApiResponse<Page<StoreListResponseDto>> getAllStores(Pageable pageable) {
        return ApiResponse.onSuccess(storeQueryService.getAllStores(pageable));
    }

}
