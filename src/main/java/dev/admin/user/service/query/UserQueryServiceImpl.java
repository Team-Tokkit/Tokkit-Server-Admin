package dev.admin.user.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.user.dto.response.UserSimpleResponseDto;
import dev.admin.user.dto.response.UserDetailResponseDto;
import dev.admin.user.entity.User;
import dev.admin.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserSimpleResponseDto> getUsers(String keyword, Pageable pageable) {
        return userRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public UserDetailResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return new UserDetailResponseDto(user.getId(), user.getName(), user.getEmail(), user.getPhoneNumber(), user.getIsDormant(), user.getCreatedAt().toString(), user.getWallet().getId());
    }
}
