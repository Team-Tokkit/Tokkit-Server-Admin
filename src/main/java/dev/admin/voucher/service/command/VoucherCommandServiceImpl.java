package dev.admin.voucher.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.global.entity.VoucherImage;
import dev.admin.global.entity.VoucherStore;
import dev.admin.merchant.entity.Merchant;
import dev.admin.store.entity.Store;
import dev.admin.store.repository.StoreRepository;
import dev.admin.voucher.dto.request.CreateVoucherRequestDto;
import dev.admin.voucher.dto.request.UpdateVoucherRequestDto;
import dev.admin.voucher.entity.Voucher;
import dev.admin.voucher.repository.VoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherCommandServiceImpl implements VoucherCommandService {

    private final VoucherRepository voucherRepository;
    private final StoreRepository storeRepository;

    // 바우처 생성
    @Override
    @Transactional
    public void createVoucher(CreateVoucherRequestDto requestDto) {
        Voucher voucher = requestDto.toEntity();

        List<Store> stores = storeRepository.findAllById(requestDto.storeIds());
        for (Store store : stores) {
            Merchant merchant = store.getMerchant();
            voucher.setMerchant(merchant);
            VoucherStore voucherStore = VoucherStore.builder()
                    .voucher(voucher)
                    .store(store)
                    .build();
            voucher.getVoucherStores().add(voucherStore);
        }

        VoucherImage voucherImage = requestDto.toVoucherImage(voucher);
        voucher.setImage(voucherImage);

        voucherRepository.save(voucher);
    }

    // 바우처 삭제
    @Override
    @Transactional
    public void deleteVoucher(Long voucherId) {
        if (!voucherRepository.existsById(voucherId)) {
            throw new GeneralException(ErrorStatus.VOUCHER_NOT_FOUND);
        }
        voucherRepository.deleteVoucherOwnershipsByVoucherId(voucherId);

        voucherRepository.deleteById(voucherId);
    }

    // 바우처 수정
    @Override
    @Transactional
    public void updateVoucher(Long voucherId, UpdateVoucherRequestDto updateDto) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.VOUCHER_NOT_FOUND));

        if (updateDto.description() != null) {
            voucher.setDescription(updateDto.description());
        }
        if (updateDto.detailDescription() != null) {
            voucher.setDetailDescription(updateDto.detailDescription());
        }
        if (updateDto.price() != null) {
            voucher.setPrice(updateDto.price());
        }
        if (updateDto.contact() != null) {
            voucher.setContact(updateDto.contact());
        }
    }

}
