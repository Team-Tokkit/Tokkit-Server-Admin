package dev.admin.notice.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.notice.dto.request.NoticeRequestDto;
import dev.admin.notice.dto.response.NoticeResponseDto;
import dev.admin.notice.service.command.NoticeCommandService;
import dev.admin.notice.service.command.NoticeCommandServiceImpl;
import dev.admin.notice.service.query.NoticeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api/notice")
@RequiredArgsConstructor
@Tag(name = "Notice", description = "공지사항 관련 API")
public class NoticeController {

    private final NoticeCommandService noticeCommandService;

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public Page<NoticeResponseDto> getNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword // ✅ 검색 키워드
    ) {
        return noticeQueryService.getNotices(page, keyword);
    }


    @GetMapping("/{noticeId}")
    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세를 조회하는 API입니다.")
    public ApiResponse<NoticeResponseDto> getNotice(@PathVariable Long noticeId) {
        return ApiResponse.onSuccess(noticeQueryService.getNotice(noticeId));
    }

    @PostMapping
    @Operation(summary = "공지사항 생성", description = "공지사항을 생성하는 API입니다.")
    public ApiResponse<Long> createNotice(@RequestBody NoticeRequestDto requestDto) {
        return ApiResponse.onSuccess(noticeCommandService.createNotice(requestDto));
    }

    @PutMapping("/{noticeId}")
    @Operation(summary = "공지사항 수정", description = "공지사항을 수정하는 API입니다.")
    public ApiResponse<Void> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeRequestDto requestDto) {
        noticeCommandService.updateNotice(noticeId, requestDto);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/{noticeId}")
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제하는 API입니다.")
    public ApiResponse<Void> deleteNotice(@PathVariable Long noticeId) {
        noticeCommandService.deleteNotice(noticeId);
        return ApiResponse.onSuccess(null);
    }
}
