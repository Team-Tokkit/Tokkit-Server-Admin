package dev.admin.unified_logs.dto.response;

import java.time.LocalDateTime;

import dev.admin.api_request_log.entity.ApiRequestLog;
import dev.admin.login_log.entity.LoginLog;
import dev.admin.system_error_log.entity.SystemErrorLog;
import dev.admin.transaction.entity.Transaction;

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
	public static UnifiedLogResponseDto fromLoginLog(LoginLog log) {
		return new UnifiedLogResponseDto(
			log.getId(),
			"LOGIN",
			log.getTraceId(),
			log.getUserId(),
			log.getMerchantId(),
			log.getTimestamp(),
			log.getEvent() + " - " + log.getIpAddress(),
			log.getUserAgent(),
			log.getSuccess() ? "SUCCESS" : "FAILURE"
		);
	}

	public static UnifiedLogResponseDto fromApiRequestLog(ApiRequestLog log) {
		return new UnifiedLogResponseDto(
			log.getId(),
			"API",
			log.getTraceId(),
			log.getUserId(),
			log.getMerchantId(),
			log.getTimestamp(),
			log.getMethod() + " " + log.getEndpoint(),
			log.getQueryParams() + "\n" + log.getRequestBody(),
			String.valueOf(log.getResponseStatus())
		);
	}

	public static UnifiedLogResponseDto fromSystemErrorLog(SystemErrorLog log) {
		return new UnifiedLogResponseDto(
			log.getId(),
			"ERROR",
			log.getTraceId(),
			log.getUserId(),
			log.getMerchantId(),
			log.getTimestamp(),
			log.getEndpoint() + " - " + log.getErrorMessage(),
			log.getStackTrace(),
			log.getSeverity().toString()
		);
	}

	public static UnifiedLogResponseDto fromTransactionLog(Transaction log) {
		return new UnifiedLogResponseDto(
			log.getId(),
			"TRANSACTION",
			log.getTraceId(),
			null,
			null,
			log.getCreatedAt(),
			log.getType() + " - " + log.getAmount(),
			log.getDescription(),
			log.getStatus().toString()
		);
	}
}
