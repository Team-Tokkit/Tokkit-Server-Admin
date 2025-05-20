package dev.admin.user.service.command;

import dev.admin.user.dto.request.UpdateUserRequestDto;
import dev.admin.user.dto.request.UpdateUserStatusRequestDto;

public interface UserCommandService {
    void updateUser(Long userId, UpdateUserRequestDto dto);
    void updateUserStatus(Long userId, UpdateUserStatusRequestDto dto);
}
