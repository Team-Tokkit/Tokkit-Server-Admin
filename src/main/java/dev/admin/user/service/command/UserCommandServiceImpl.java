package dev.admin.user.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.global.util.PasswordUtil;
import dev.admin.user.entity.User;
import dev.admin.user.dto.request.UpdateUserRequestDto;
import dev.admin.user.dto.request.UpdateUserStatusRequestDto;
import dev.admin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    @Override
    public void updateUser(Long userId, UpdateUserRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        String encodedPassword = PasswordUtil.encode(dto.simplePassword());
        user.update(dto.name(), encodedPassword, dto.phone());
        userRepository.save(user);
    }

    @Override
    public void updateUserStatus(Long userId, UpdateUserStatusRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        user.changeStatus(dto.isDormant());
        userRepository.save(user);
    }
}
