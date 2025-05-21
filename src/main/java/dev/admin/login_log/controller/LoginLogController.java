package dev.admin.login_log.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.login_log.dto.request.LoginLogSearchDto;
import dev.admin.login_log.dto.response.LoginLogResponseDto;
import dev.admin.login_log.dto.response.LoginLogSimpleResponseDto;
import dev.admin.login_log.service.query.LoginLogQueryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api/login-logs")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogQueryService queryService;

    @GetMapping("/{id}")
    public ApiResponse<LoginLogResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.onSuccess(queryService.getById(id));
    }

    @GetMapping
    public ApiResponse<Page<LoginLogSimpleResponseDto>> search(
            @ModelAttribute LoginLogSearchDto condition,
            @ParameterObject  Pageable pageable) {
        return ApiResponse.onSuccess(queryService.search(condition, pageable));
    }
}
