package dev.admin.unified_logs.dto.response;

import java.time.LocalDateTime;

import dev.admin.unified_logs.entity.UnifiedLog;

public record UnifiedLogResponseDto(
	Long id,
	String logType,
	String traceId,
	Long userId,
	Long merchantId,
	LocalDateTime timestamp,
	String summary,
	String detail,
	String statusOrSeverity
) {
	public static UnifiedLogResponseDto from(UnifiedLog log) {
		return new UnifiedLogResponseDto(
			log.getId(),
			log.getLogType(),
			log.getTraceId(),
			log.getUserId(),
			log.getMerchantId(),
			log.getTimestamp(),
			log.getSummary(),
			log.getDetail(),
			log.getStatusOrSeverity()
		);
	}
}
