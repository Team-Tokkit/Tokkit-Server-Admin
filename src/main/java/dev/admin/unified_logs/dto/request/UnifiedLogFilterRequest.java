package dev.admin.unified_logs.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record UnifiedLogFilterRequest(
	@Schema(description = "로그 traceId (선택)")
	String traceId,

	@Schema(description = "사용자 ID (선택)")
	Long userId,

	@Schema(description = "가맹점 ID (선택)")
	Long merchantId,

	@Schema(description = "로그 타입 필터: LOGIN, ERROR, API, TRANSACTION")
	List<String> logTypes,

	@Schema(description = "조회 시작 시간 (ISO 8601)")
	LocalDateTime from,

	@Schema(description = "조회 종료 시간 (ISO 8601)")
	LocalDateTime to
) {}
