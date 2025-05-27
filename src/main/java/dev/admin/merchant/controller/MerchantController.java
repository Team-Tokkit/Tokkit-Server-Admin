package dev.admin.merchant.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.merchant.dto.request.UpdateMerchantRequestDto;
import dev.admin.merchant.dto.request.UpdateMerchantStatusRequestDto;
import dev.admin.merchant.dto.response.MerchantDetailResponseDto;
import dev.admin.merchant.dto.response.MerchantSimpleResponseDto;
import dev.admin.merchant.service.command.MerchantCommandService;
import dev.admin.merchant.service.query.MerchantQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/merchants")
public class MerchantController {

    private final MerchantCommandService merchantCommandService;
    private final MerchantQueryService merchantQueryService;

    @PutMapping("/{merchantId}")
    public ApiResponse<Long> updateMerchant(@PathVariable Long merchantId,
                                            @RequestBody UpdateMerchantRequestDto requestDto) {
        merchantCommandService.updateMerchant(merchantId, requestDto);
        return ApiResponse.onSuccess(merchantId);
    }

    @PatchMapping("/{merchantId}/status")
    public ApiResponse<Long> updateMerchantStatus(@PathVariable Long merchantId,
                                                  @RequestBody UpdateMerchantStatusRequestDto requestDto) {
        merchantCommandService.updateMerchantStatus(merchantId, requestDto);
        return ApiResponse.onSuccess(merchantId);
    }

    @GetMapping
    public ApiResponse<Page<MerchantSimpleResponseDto>> getMerchants(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ApiResponse.onSuccess(merchantQueryService.getMerchants(keyword, pageable));
    }

    @GetMapping("/{merchantId}")
    public ApiResponse<MerchantDetailResponseDto> getMerchant(@PathVariable Long merchantId) {
        return ApiResponse.onSuccess(merchantQueryService.getMerchant(merchantId));
    }
}
