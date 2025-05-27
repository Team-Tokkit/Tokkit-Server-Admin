package dev.admin.merchant.service.command;

import dev.admin.merchant.dto.request.UpdateMerchantRequestDto;
import dev.admin.merchant.dto.request.UpdateMerchantStatusRequestDto;

public interface MerchantCommandService {
    void updateMerchant(Long merchantId, UpdateMerchantRequestDto requestDto);
    void updateMerchantStatus(Long merchantId, UpdateMerchantStatusRequestDto requestDto);
}
