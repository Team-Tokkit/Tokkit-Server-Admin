package dev.admin.merchant.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.global.util.PasswordUtil;
import dev.admin.merchant.dto.request.UpdateMerchantRequestDto;
import dev.admin.merchant.dto.request.UpdateMerchantStatusRequestDto;
import dev.admin.merchant.entity.Merchant;
import dev.admin.merchant.repository.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MerchantCommandServiceImplTest {

    private MerchantRepository merchantRepository;
    private MerchantCommandServiceImpl merchantCommandService;
    @Captor
    ArgumentCaptor<String> encodedPasswordCaptor;

    @BeforeEach
    void setUp() {
        merchantRepository = mock(MerchantRepository.class);
        merchantCommandService = new MerchantCommandServiceImpl(merchantRepository);
    }

    @Test
    void updateMerchant_shouldEncryptPassword_andCallUpdate() {
        // given
        Merchant merchant = mock(Merchant.class);
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));
        UpdateMerchantRequestDto dto = new UpdateMerchantRequestDto("홍가게", "010-1234-5678", "000000");

        // when
        merchantCommandService.updateMerchant(1L, dto);

        // then
        verify(merchant).update(
                eq("홍가게"),
                eq("010-1234-5678"),
                encodedPasswordCaptor.capture()
        );

        String encoded = encodedPasswordCaptor.getValue();

        assertThat(PasswordUtil.matches("000000", encoded)).isTrue();
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
