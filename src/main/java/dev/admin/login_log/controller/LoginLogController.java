package dev.admin.login_log.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.login_log.dto.request.LoginLogSearchDto;
import dev.admin.login_log.dto.response.LoginLogResponseDto;
import dev.admin.login_log.dto.response.LoginLogSimpleResponseDto;
import dev.admin.login_log.enums.Event;
import dev.admin.login_log.service.query.LoginLogQueryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long merchantId,
            @RequestParam(required = false) Boolean success,
            @RequestParam(required = false) Event event,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        LoginLogSearchDto condition = new LoginLogSearchDto(userId, merchantId, success, event);
        return ApiResponse.onSuccess(queryService.search(condition, pageable));
    }
}
