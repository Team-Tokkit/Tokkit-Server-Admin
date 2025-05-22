package dev.admin.voucher.repository;

import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.voucher.dto.request.VoucherSearchRequest;
import dev.admin.voucher.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherCustomRepository {
    Page<Voucher> searchVouchers(VoucherSearchRequest request, Pageable pageable);
    Page<StoreListResponseDto> findStoresByVoucherId(Long voucherId, Pageable pageable);
}
