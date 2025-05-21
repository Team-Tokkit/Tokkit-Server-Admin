package dev.admin.system_error_log.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.system_error_log.dto.request.SystemErrorLogSearchDto;
import dev.admin.system_error_log.dto.response.SystemErrorLogResponseDto;
import dev.admin.system_error_log.dto.response.SystemErrorLogSimpleResponseDto;
import dev.admin.system_error_log.service.query.SystemErrorLogQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api/system-error-logs")
@RequiredArgsConstructor
public class SystemErrorLogController {

    private final SystemErrorLogQueryService queryService;

    @GetMapping("/{id}")
    public ApiResponse<SystemErrorLogResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.onSuccess(queryService.getById(id));
    }
    @GetMapping
    public ApiResponse<Page<SystemErrorLogSimpleResponseDto>> search(
            SystemErrorLogSearchDto condition,
            Pageable pageable) {
        return ApiResponse.onSuccess(queryService.search(condition, pageable));
    }
}
