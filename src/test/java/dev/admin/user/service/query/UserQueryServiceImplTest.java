package dev.admin.user.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.user.dto.response.UserDetailResponseDto;
import dev.admin.user.dto.response.UserSimpleResponseDto;
import dev.admin.user.entity.User;
import dev.admin.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserQueryServiceImplTest {

    private UserRepository userRepository;
    private UserQueryServiceImpl userQueryService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userQueryService = new UserQueryServiceImpl(userRepository);
    }

    @Test
    void getUsers_shouldReturnPageOfDto() {
        // given
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getName()).thenReturn("홍길동");
        when(user.getEmail()).thenReturn("hong@mail.com");
        when(user.getPhoneNumber()).thenReturn("01012345678");
        when(user.getIsDormant()).thenReturn(false);
        when(user.getCreatedAt()).thenReturn(LocalDateTime.now());

        UserSimpleResponseDto dto = new UserSimpleResponseDto(
                1L, "홍길동", "hong@mail.com", "01012345678", false, LocalDateTime.now()
        );

        when(userRepository.findByKeyword(eq("홍"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));
        // when
        Page<UserSimpleResponseDto> result = userQueryService.getUsers("홍", PageRequest.of(0, 10));

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).name()).isEqualTo("홍길동");
    }

    @Test
    void getUser_shouldReturnUserDetailDto() {
        // given
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getName()).thenReturn("홍길동");
        when(user.getEmail()).thenReturn("hong@mail.com");
        when(user.getPhoneNumber()).thenReturn("01012345678");
        when(user.getIsDormant()).thenReturn(false);
        when(user.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(user.getWallet()).thenReturn(mock(dev.admin.wallet.entity.Wallet.class));
        when(user.getWallet().getId()).thenReturn(99L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        UserDetailResponseDto result = userQueryService.getUser(1L);

        // then
        assertThat(result.name()).isEqualTo("홍길동");
        assertThat(result.walletId()).isEqualTo(99L);
    }

    @Test
    void getUser_shouldThrowIfNotFound() {
        // given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // when
        GeneralException ex = assertThrows(GeneralException.class, () -> {
            userQueryService.getUser(999L);
        });

        // then
        assertEquals(ErrorStatus.USER_NOT_FOUND, ex.getErrorStatus());
    }

}
