package dev.admin.merchant.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.merchant.dto.response.MerchantDetailResponseDto;
import dev.admin.merchant.dto.response.MerchantSimpleResponseDto;
import dev.admin.merchant.entity.Merchant;
import dev.admin.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantQueryServiceImpl implements MerchantQueryService {

    private final MerchantRepository merchantRepository;

    @Override
    public Page<MerchantSimpleResponseDto> getMerchants(String keyword, Pageable pageable) {
        return merchantRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public MerchantDetailResponseDto getMerchant(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MERCHANT_NOT_FOUND));
        System.out.println("merchant = " + merchant);
        return MerchantDetailResponseDto.from(merchant);
    }
}
