package dev.admin.voucher.service.query;

import dev.admin.voucher.dto.request.VoucherSearchRequest;
import dev.admin.voucher.dto.response.VoucherSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherQueryService {
    Page<VoucherSimpleResponseDto> searchVouchers(VoucherSearchRequest request, Pageable pageable);
}

