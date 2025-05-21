package dev.admin.voucher.service.command;

import dev.admin.global.entity.VoucherImage;
import dev.admin.global.entity.VoucherStore;
import dev.admin.global.repository.VoucherImageRepository;
import dev.admin.global.repository.VoucherStoreRepository;
import dev.admin.store.entity.Store;
import dev.admin.store.repository.StoreRepository;
import dev.admin.voucher.dto.request.CreateVoucherRequestDto;
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
    private final VoucherImageRepository voucherImageRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public void createVoucher(CreateVoucherRequestDto requestDto) {

        // 1. Voucher 엔티티로 반환
        Voucher voucher = requestDto.toEntity();

        // 2. 선택된 Store들 조회 후 VoucherStore 생성
        List<Store> stores = storeRepository.findAllById(requestDto.storeIds());
        for (Store store : stores) {
            VoucherStore voucherStore = VoucherStore.builder()
                    .voucher(voucher)
                    .store(store)
                    .build();
            voucher.getVoucherStores().add(voucherStore);
        }

        // 3. 이미지 저장
        VoucherImage voucherImage = requestDto.toVoucherImage(voucher);
        voucher.setImage(voucherImage);

        // 4. Voucher 엔티티 저장
        voucherRepository.save(voucher);
    }
}
