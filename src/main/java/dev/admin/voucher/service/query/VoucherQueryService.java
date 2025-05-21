package dev.admin.voucher.service.query;

import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.voucher.dto.request.VoucherSearchRequest;
import dev.admin.voucher.dto.response.VoucherResponseDto;
import dev.admin.voucher.dto.response.VoucherSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherQueryService {
    // 전체 조회 및 검색
    Page<VoucherSimpleResponseDto> searchVouchers(VoucherSearchRequest request, Pageable pageable);

    // 상세 조회
    VoucherResponseDto getVoucherDetail(Long id, Pageable pageable);

    // 바우처 사용처 전제 조회
    Page<StoreListResponseDto> getAllStoresByVoucherId(Long id, Pageable pageable);

}

