package dev.admin.merchant.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.global.util.PasswordUtil;
import dev.admin.merchant.dto.request.UpdateMerchantRequestDto;
import dev.admin.merchant.dto.request.UpdateMerchantStatusRequestDto;
import dev.admin.merchant.entity.Merchant;
import dev.admin.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantCommandServiceImpl implements MerchantCommandService {

    private final MerchantRepository merchantRepository;
    @Override
    public void updateMerchant(Long merchantId, UpdateMerchantRequestDto dto) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MERCHANT_NOT_FOUND));
        String encodedPassword = PasswordUtil.encode(dto.simplePassword());
        merchant.update(dto.name(), dto.contact(), encodedPassword);
        merchantRepository.save(merchant);
    }

    @Override
    public void updateMerchantStatus(Long merchantId, UpdateMerchantStatusRequestDto dto) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MERCHANT_NOT_FOUND));

        merchant.changeStatus(dto.isDormant());
        merchantRepository.save(merchant);
    }
}
