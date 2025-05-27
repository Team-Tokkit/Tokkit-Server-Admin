package dev.admin.merchant.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.merchant.dto.response.MerchantDetailResponseDto;
import dev.admin.merchant.dto.response.MerchantSimpleResponseDto;
import dev.admin.merchant.entity.Merchant;
import dev.admin.merchant.repository.MerchantRepository;
import dev.admin.wallet.entity.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MerchantQueryServiceImplTest {

    private MerchantRepository merchantRepository;
    private MerchantQueryServiceImpl merchantQueryService;

    @BeforeEach
    void setUp() {
        merchantRepository = mock(MerchantRepository.class);
        merchantQueryService = new MerchantQueryServiceImpl(merchantRepository);
    }

    @Test
    void getMerchants_shouldReturnPageOfDto() {
        // given
        MerchantSimpleResponseDto dto = new MerchantSimpleResponseDto(
                1L, "홍가게", "hong@mail.com", "01012345678", false, LocalDateTime.now()
        );

        when(merchantRepository.findByKeyword(eq("홍"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // when
        Page<MerchantSimpleResponseDto> result = merchantQueryService.getMerchants("홍", PageRequest.of(0, 10));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).name()).isEqualTo("홍가게");
    }

    @Test
    void getMerchant_shouldReturnDetailDto() throws Exception {

        // given
        Merchant merchant = Merchant.builder()
                .id(1L)
                .name("홍가게")
                .email("hong@mail.com")
                .phoneNumber("01012345678")
                .isDormant(false)
                .businessNumber("123-45-67890")
                .simplePassword("1234")
                .build();

        Field createdAtField = merchant.getClass().getSuperclass().getDeclaredField("createdAt");
        createdAtField.setAccessible(true);
        createdAtField.set(merchant, LocalDateTime.of(2025, 5, 20, 10, 0));

        Wallet wallet = mock(Wallet.class);
        when(wallet.getId()).thenReturn(777L);

        Field walletField = merchant.getClass().getDeclaredField("wallet");
        walletField.setAccessible(true);
        walletField.set(merchant, wallet);

        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));

        // when
        MerchantDetailResponseDto result = merchantQueryService.getMerchant(1L);

        // then
        assertThat(result.name()).isEqualTo("홍가게");
        assertThat(result.walletId()).isEqualTo(777L);
    }


    @Test
    void getMerchant_shouldThrowIfNotFound() {
        // given
        when(merchantRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        GeneralException ex = assertThrows(GeneralException.class, () -> {
            merchantQueryService.getMerchant(99L);
        });

        assertThat(ex.getErrorStatus()).isEqualTo(ErrorStatus.MERCHANT_NOT_FOUND);
    }
}
