package dev.admin.notice.service.query;

import dev.admin.notice.dto.response.NoticeResponseDto;
import org.springframework.data.domain.Page;

public interface NoticeQueryService {
    public Page<NoticeResponseDto> getNotices(int page);

    public NoticeResponseDto getNotice(Long noticeId);
}
