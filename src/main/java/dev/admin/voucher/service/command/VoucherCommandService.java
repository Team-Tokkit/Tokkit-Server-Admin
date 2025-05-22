package dev.admin.voucher.service.command;

import dev.admin.voucher.dto.request.CreateVoucherRequestDto;
import dev.admin.voucher.dto.request.UpdateVoucherRequestDto;

public interface VoucherCommandService {
    void createVoucher(CreateVoucherRequestDto requestDto);
    void deleteVoucher(Long voucherId);
    void updateVoucher(Long voucherId, UpdateVoucherRequestDto updateVoucherRequestDto);
}