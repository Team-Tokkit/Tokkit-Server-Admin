package dev.admin.merchant.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.merchant.dto.request.UpdateMerchantRequestDto;
import dev.admin.merchant.dto.request.UpdateMerchantStatusRequestDto;
import dev.admin.merchant.entity.Merchant;
import dev.admin.merchant.repository.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MerchantCommandServiceImplTest {

    private MerchantRepository merchantRepository;
    private MerchantCommandServiceImpl merchantCommandService;

    @BeforeEach
    void setUp() {
        merchantRepository = mock(MerchantRepository.class);
        merchantCommandService = new MerchantCommandServiceImpl(merchantRepository);
    }

    @Test
    void updateMerchant_shouldCallEntityUpdate() {
        // given
        Merchant merchant = mock(Merchant.class);
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));

        UpdateMerchantRequestDto dto = new UpdateMerchantRequestDto("홍가게", "010-1234-5678", "1234");

        // when
        merchantCommandService.updateMerchant(1L, dto);

        // then
        verify(merchant).update("홍가게", "010-1234-5678", "1234");
    }

    @Test
    void updateMerchant_shouldThrowIfNotFound() {
        // given
        when(merchantRepository.findById(2L)).thenReturn(Optional.empty());
        UpdateMerchantRequestDto dto = new UpdateMerchantRequestDto("test", "010", "1234");

        // when & then
        GeneralException ex = assertThrows(GeneralException.class, () -> {
            merchantCommandService.updateMerchant(2L, dto);
        });

        assertEquals(ErrorStatus.MERCHANT_NOT_FOUND, ex.getErrorStatus());
    }

    @Test
    void updateMerchantStatus_shouldCallChangeStatus() {
        // given
        Merchant merchant = mock(Merchant.class);
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));

        UpdateMerchantStatusRequestDto dto = new UpdateMerchantStatusRequestDto(true);

        // when
        merchantCommandService.updateMerchantStatus(1L, dto);

        // then
        verify(merchant).changeStatus(true);
    }
}
