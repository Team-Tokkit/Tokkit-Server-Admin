package dev.admin.api_request_log.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record ApiRequestLogPageResponseDto(
        List<ApiRequestLogPageItemDto> content,
        PageableInfo pageable) {
    public static ApiRequestLogPageResponseDto from(Page<ApiRequestLogPageItemDto> page) {
        return new ApiRequestLogPageResponseDto(
                page.getContent(),
                PageableInfo.from(page)
        );
    }
}
