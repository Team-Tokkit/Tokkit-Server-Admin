package dev.admin.unified_logs.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.unified_logs.dto.request.UnifiedLogFilterRequest;
import dev.admin.unified_logs.dto.response.UnifiedLogResponseDto;
import dev.admin.unified_logs.service.query.UnifiedLogQueryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/unified-logs")
public class UnifiedLogController {
	private final UnifiedLogQueryService unifiedLogQueryService;

	@GetMapping
	public ApiResponse<Page<UnifiedLogResponseDto>> getLogs(
		@RequestParam(required = false) String traceId,
		@RequestParam(required = false) Long userId,
		@RequestParam(required = false) Long merchantId,
		@RequestParam(required = false) List<String> logTypes,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
		@PageableDefault(size = 20, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
	) {
		UnifiedLogFilterRequest filter = new UnifiedLogFilterRequest(traceId, userId, merchantId, logTypes, from, to);
		return ApiResponse.onSuccess(unifiedLogQueryService.getLogs(filter, pageable));
	}
}
