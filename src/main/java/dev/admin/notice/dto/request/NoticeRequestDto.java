package dev.admin.notice.dto.request;

import dev.admin.notice.entity.Notice;

public record NoticeRequestDto(
        String title,
        String content
) {
    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }
}
