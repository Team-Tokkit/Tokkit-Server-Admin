package dev.admin.merchant.service.query;

import dev.admin.merchant.dto.response.MerchantDetailResponseDto;
import dev.admin.merchant.dto.response.MerchantSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MerchantQueryService {
    Page<MerchantSimpleResponseDto> getMerchants(String keyword, Pageable pageable);
    MerchantDetailResponseDto getMerchant(Long merchantId);
}
