package dev.admin.voucher.service.query;

import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.voucher.dto.request.VoucherSearchRequest;
import dev.admin.voucher.dto.response.VoucherResponseDto;
import dev.admin.voucher.dto.response.VoucherSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherQueryService {
    Page<VoucherSimpleResponseDto> searchVouchers(VoucherSearchRequest request, Pageable pageable);
    VoucherResponseDto getVoucherDetail(Long id, Pageable pageable);
    Page<StoreListResponseDto> getAllStoresByVoucherId(Long id, Pageable pageable);

}

