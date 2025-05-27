package dev.admin.user.service.query;

import dev.admin.user.dto.response.UserDetailResponseDto;
import dev.admin.user.dto.response.UserSimpleResponseDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface UserQueryService {
    Page<UserSimpleResponseDto> getUsers(String keyword, Pageable pageable);

    UserDetailResponseDto getUser(Long userId);
}
