package dev.admin.merchant.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.merchant.dto.request.UpdateMerchantRequestDto;
import dev.admin.merchant.dto.request.UpdateMerchantStatusRequestDto;
import dev.admin.merchant.entity.Merchant;
import dev.admin.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantCommandServiceImpl implements MerchantCommandService {

    private final MerchantRepository merchantRepository;

    @Override
    public void updateMerchant(Long merchantId, UpdateMerchantRequestDto requestDto) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MERCHANT_NOT_FOUND));

        merchant.update(requestDto.name(), requestDto.contact(), requestDto.simplePassword());
        merchantRepository.save(merchant);
    }

    @Override
    public void updateMerchantStatus(Long merchantId, UpdateMerchantStatusRequestDto requestDto) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MERCHANT_NOT_FOUND));

        merchant.changeStatus(requestDto.isDormant());
        merchantRepository.save(merchant);
    }
}
