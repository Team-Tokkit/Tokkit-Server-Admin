package dev.admin.notice.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.notice.entity.Notice;
import dev.admin.notice.dto.response.NoticeResponseDto;
import dev.admin.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeRepository noticeRepository;

    private final int SIZE = 7;

    public Page<NoticeResponseDto> getNotices(int page, String keyword) {
        if (page < 0) {
            throw new GeneralException(ErrorStatus.INVALID_PAGE_NUMBER);
        }
        PageRequest pageRequest = PageRequest.of(page, SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Notice> notices = noticeRepository.findByTitleContainingIgnoreCase(keyword, pageRequest);

        return notices.map(NoticeResponseDto::from);
    }

    @Override
    public NoticeResponseDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOTICE_NOT_FOUND));

        return NoticeResponseDto.from(notice);
    }
}
