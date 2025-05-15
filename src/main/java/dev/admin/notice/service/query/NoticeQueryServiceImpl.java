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

    @Override
    public Page<NoticeResponseDto> getNotices(int page) {
        Page<Notice> notices = noticeRepository.findAll(
                PageRequest.of(page, SIZE)
                        .withSort(Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return notices.map(NoticeResponseDto::from);
    }

    @Override
    public NoticeResponseDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOTICE_NOT_FOUND));

        return NoticeResponseDto.from(notice);
    }
}
