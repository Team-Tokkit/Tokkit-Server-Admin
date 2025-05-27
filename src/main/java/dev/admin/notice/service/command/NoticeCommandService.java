package dev.admin.notice.service.command;

import dev.admin.notice.dto.request.NoticeRequestDto;

public interface NoticeCommandService {

    public Long createNotice(NoticeRequestDto requestDto);

    public String updateNotice(Long noticeId, NoticeRequestDto requestDto);

    public String updateNoticeStatus(Long noticeId,boolean isDeleted);

}
