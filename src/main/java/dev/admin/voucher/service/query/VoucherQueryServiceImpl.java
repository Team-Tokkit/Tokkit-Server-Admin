package dev.admin.voucher.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.voucher.dto.request.VoucherSearchRequest;
import dev.admin.voucher.dto.response.VoucherResponseDto;
import dev.admin.voucher.dto.response.VoucherSimpleResponseDto;
import dev.admin.voucher.entity.Voucher;
import dev.admin.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherQueryServiceImpl implements VoucherQueryService {

    private final VoucherRepository voucherRepository;
    private final String imageProxyBaseUrl;

    // 바우처 목록 조회 (카테고리 필터링, 검색)
    public Page<VoucherSimpleResponseDto> searchVouchers(VoucherSearchRequest request, Pageable pageable) {
        Page<Voucher> vouchers = voucherRepository.searchVouchers(request, pageable);

        return vouchers.map(voucher -> VoucherSimpleResponseDto.from(voucher, imageProxyBaseUrl));
    }

    // 바우처 상세 조회
    public VoucherResponseDto getVoucherDetail(Long id, Pageable pageable) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.VOUCHER_NOT_FOUND));

        Pageable limitedPageable = Pageable.ofSize(5);

        Page<StoreListResponseDto> stores = voucherRepository.findStoresByVoucherId(id, limitedPageable);

        return VoucherResponseDto.from(voucher, stores, imageProxyBaseUrl);
    }

    // 바우처 전체 조회
    public Page<StoreListResponseDto> getAllStoresByVoucherId(Long voucherId, Pageable pageable) {
        return voucherRepository.findStoresByVoucherId(voucherId, pageable);
    }

}
