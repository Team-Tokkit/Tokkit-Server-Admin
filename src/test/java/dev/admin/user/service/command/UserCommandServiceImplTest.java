package dev.admin.user.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.global.util.PasswordUtil;
import dev.admin.user.dto.request.UpdateUserRequestDto;
import dev.admin.user.dto.request.UpdateUserStatusRequestDto;
import dev.admin.user.entity.User;
import dev.admin.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserCommandServiceImpl userCommandService;

    @Captor
    ArgumentCaptor<String> simplePasswordCaptor;

    @Test
    void updateUser_shouldEncryptPasswordAndCallUpdate() {
        // given
        User user = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UpdateUserRequestDto dto = new UpdateUserRequestDto("홍길동", "01012345678", "000000");

        // when
        userCommandService.updateUser(1L, dto);

        // then
        verify(user).update(eq("홍길동"), simplePasswordCaptor.capture(), eq("01012345678"));

        String capturedPassword = simplePasswordCaptor.getValue();
        assertThat(PasswordUtil.matches("000000", capturedPassword)).isTrue();
    }

    @Test
    void updateUser_shouldThrowIfNotFound() {
        // given
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        UpdateUserRequestDto dto = new UpdateUserRequestDto("홍길동", "000000", "01012345678");

        // when & then
        GeneralException ex = assertThrows(GeneralException.class, () -> {
            userCommandService.updateUser(99L, dto);
        });

        assertEquals(ErrorStatus.USER_NOT_FOUND, ex.getErrorStatus());
    }

    @Test
    void updateUserStatus_shouldCallChangeStatus() {
        // given
        User user = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UpdateUserStatusRequestDto dto = new UpdateUserStatusRequestDto(true);

        // when
        userCommandService.updateUserStatus(1L, dto);

        // then
        verify(user).changeStatus(true);
    }
}
