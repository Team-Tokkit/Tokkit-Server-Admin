package dev.admin.notice.service.command;

import dev.admin.notice.dto.request.NoticeRequestDto;

public interface NoticeCommandService {

    public Long createNotice(NoticeRequestDto requestDto);

    public void updateNotice(Long noticeId, NoticeRequestDto requestDto);

    public void deleteNotice(Long noticeId);

}
