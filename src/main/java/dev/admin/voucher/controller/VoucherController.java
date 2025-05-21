package dev.admin.voucher.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.voucher.dto.request.CreateVoucherRequestDto;
import dev.admin.voucher.service.command.VoucherCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/vouchers")
public class VoucherController {

    private final VoucherCommandService voucherCommandService;

    @PostMapping("/create")
    @Operation(summary = "바우처 생성", description = "바우처를 생성합니다.")
    public ApiResponse<String> createVoucher(@RequestBody CreateVoucherRequestDto requestDto) {
        voucherCommandService.createVoucher(requestDto);
        return ApiResponse.onSuccess("바우처 생성 완료");
    }
}
