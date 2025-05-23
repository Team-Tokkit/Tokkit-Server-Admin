package dev.admin.notice.dto.response;

import java.time.LocalDateTime;

public record NoticeResponseSimpleDto(
        Long id,
        String title,
        String content,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
