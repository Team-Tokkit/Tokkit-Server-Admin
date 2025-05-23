package dev.admin.notice.dto.response;

import dev.admin.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeResponseDto(
         Long id,
         String title,
         String content,
         boolean isDeleted,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
) {
    public static NoticeResponseDto from(Notice notice) {
        return new NoticeResponseDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.isDeleted(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    }
}
