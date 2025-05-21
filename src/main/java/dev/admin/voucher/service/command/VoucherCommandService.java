package dev.admin.voucher.service.command;

import dev.admin.voucher.dto.request.CreateVoucherRequestDto;

public interface VoucherCommandService {
    void createVoucher(CreateVoucherRequestDto requestDto);
}