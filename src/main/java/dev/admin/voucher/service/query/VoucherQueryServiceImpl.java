package dev.admin.voucher.service.query;

import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.voucher.dto.request.VoucherSearchRequest;
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

    /**
     * [1] 바우처 목록 조회 - 검색 + 필터 + 정렬 포함
     */
    public Page<VoucherSimpleResponseDto> searchVouchers(VoucherSearchRequest request, Pageable pageable) {
        Page<Voucher> vouchers = voucherRepository.searchVouchers(request, pageable);

        return vouchers.map(voucher -> VoucherSimpleResponseDto.from(voucher, imageProxyBaseUrl));
    }

}
