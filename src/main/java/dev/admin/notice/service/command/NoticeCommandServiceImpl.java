package dev.admin.notice.service.command;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.notice.dto.request.NoticeRequestDto;
import dev.admin.notice.entity.Notice;
import dev.admin.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeCommandServiceImpl implements NoticeCommandService {

    private final NoticeRepository noticeRepository;

    @Override
    public Long createNotice(NoticeRequestDto requestDto) {
        Notice notice = requestDto.toEntity();
        return noticeRepository.save(notice).getId();
    }

    @Override
    public String updateNotice(Long noticeId, NoticeRequestDto requestDto) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOTICE_NOT_FOUND));
        notice.update(requestDto.title(), requestDto.content());
        noticeRepository.save(notice);
        return noticeId + "번 공지사항의 정보가 성공적으로 수정되었습니다.";
    }

    @Override
    public String updateNoticeStatus(Long noticeId, boolean isDeleted) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOTICE_NOT_FOUND));
        notice.setDeleted(isDeleted);
        noticeRepository.save(notice);

        String message = isDeleted ? "삭제" : "복구";
        return noticeId + "번 공지사항이 " + message + " 되었습니다.";
    }
}
