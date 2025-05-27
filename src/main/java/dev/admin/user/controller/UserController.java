package dev.admin.user.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.user.dto.request.UpdateUserRequestDto;
import dev.admin.user.dto.request.UpdateUserStatusRequestDto;
import dev.admin.user.dto.response.UserDetailResponseDto;
import dev.admin.user.dto.response.UserSimpleResponseDto;
import dev.admin.user.service.command.UserCommandService;
import dev.admin.user.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PutMapping("/{userId}")
    public ApiResponse<Long> updateUser(@PathVariable Long userId,
                                        @RequestBody UpdateUserRequestDto requestDto) {
        userCommandService.updateUser(userId, requestDto);
        return ApiResponse.onSuccess(userId);
    }

    @PatchMapping("/{userId}/status")
    public ApiResponse<Long> updateUserStatus(@PathVariable Long userId,
                                              @RequestBody UpdateUserStatusRequestDto requestDto) {
        userCommandService.updateUserStatus(userId, requestDto);
        return ApiResponse.onSuccess(userId);
    }

    @GetMapping
    public ApiResponse<Page<UserSimpleResponseDto>> getUsers(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ApiResponse.onSuccess(userQueryService.getUsers(keyword, pageable));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserDetailResponseDto> getUser(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userQueryService.getUser(userId));
    }
}
