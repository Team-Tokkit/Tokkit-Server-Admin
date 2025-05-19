package dev.admin.api_request_log.dto.response;

import org.springframework.data.domain.Page;

public record PageableInfo(
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    public static PageableInfo from(Page<ApiRequestLogPageItemDto> page) {
        return new PageableInfo(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
