package dev.admin.voucher.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.voucher.dto.request.CreateVoucherRequestDto;
import dev.admin.voucher.dto.request.VoucherSearchRequest;
import dev.admin.voucher.dto.response.VoucherResponseDto;
import dev.admin.voucher.dto.response.VoucherSimpleResponseDto;
import dev.admin.voucher.service.command.VoucherCommandService;
import dev.admin.voucher.service.query.VoucherQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/vouchers")
public class VoucherController {

    private final VoucherCommandService voucherCommandService;
    private final VoucherQueryService voucherQueryService;

    @PostMapping("/create")
    @Operation(summary = "바우처 생성", description = "바우처를 생성합니다.")
    public ApiResponse<String> createVoucher(@RequestBody CreateVoucherRequestDto requestDto) {
        voucherCommandService.createVoucher(requestDto);
        return ApiResponse.onSuccess("바우처 생성 완료");
    }

    @GetMapping
    @Operation(summary = "전체 바우처 조회 및 필터링/검색", description = "카테고리, 키워드로 바우처 목록을 조회합니다.")
    public ApiResponse<Page<VoucherSimpleResponseDto>> getVouchers(@ModelAttribute VoucherSearchRequest request, Pageable pageable) {
        return ApiResponse.onSuccess(voucherQueryService.searchVouchers(request, pageable));
    }

    @GetMapping("/{voucherId}")
    @Operation(summary = "바우처 상세 조회", description = "바우처의 상세 정보 및 사용처(스토어)를 조회합니다.")
    public ApiResponse<VoucherResponseDto> getVoucherDetail(
            @PathVariable Long voucherId,
            Pageable pageable
    ) {
        return ApiResponse.onSuccess(voucherQueryService.getVoucherDetail(voucherId, pageable));
    }

    @GetMapping("/details/{id}/stores")
    @Operation(summary = "바우처 사용처 전체 조회", description = "특정 바우처의 사용처 전체 목록을 조회합니다.")
    public ApiResponse<Page<StoreListResponseDto>> getAllStoresByVoucherId(@PathVariable Long id, Pageable pageable) {
        return ApiResponse.onSuccess(voucherQueryService.getAllStoresByVoucherId(id, pageable));
    }
}
