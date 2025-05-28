package dev.admin.unified_logs.service.query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.admin.api_request_log.repository.ApiRequestLogRepository;
import dev.admin.login_log.repository.LoginLogRepository;
import dev.admin.system_error_log.repository.SystemErrorLogRepository;
import dev.admin.transaction.repository.TransactionRepository;
import dev.admin.unified_logs.dto.request.UnifiedLogFilterRequest;
import dev.admin.unified_logs.dto.response.UnifiedLogResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnifiedLogQueryServiceImpl implements UnifiedLogQueryService {
	private final LoginLogRepository loginLogRepository;
	private final ApiRequestLogRepository apiRequestLogRepository;
	private final SystemErrorLogRepository systemErrorLogRepository;
	private final TransactionRepository transactionLogRepository;

	@Override
	public Page<UnifiedLogResponseDto> getLogs(UnifiedLogFilterRequest filter, Pageable pageable) {
		List<UnifiedLogResponseDto> result = new ArrayList<>();

		if (filter.logTypes() == null || filter.logTypes().contains("LOGIN")) {
			result.addAll(loginLogRepository.findAll().stream()
				.filter(log -> matchesFilter(log.getTraceId(), log.getUserId(), log.getMerchantId(), log.getTimestamp(), filter))
				.map(UnifiedLogResponseDto::fromLoginLog).toList());
		}

		if (filter.logTypes() == null || filter.logTypes().contains("API")) {
			result.addAll(apiRequestLogRepository.findAll().stream()
				.filter(log -> matchesFilter(log.getTraceId(), log.getUserId(), log.getMerchantId(), log.getTimestamp(), filter))
				.map(UnifiedLogResponseDto::fromApiRequestLog).toList());
		}

		if (filter.logTypes() == null || filter.logTypes().contains("ERROR")) {
			result.addAll(systemErrorLogRepository.findAll().stream()
				.filter(log -> matchesFilter(log.getTraceId(), log.getUserId(), log.getMerchantId(), log.getTimestamp(), filter))
				.map(UnifiedLogResponseDto::fromSystemErrorLog).toList());
		}

		if (filter.logTypes() == null || filter.logTypes().contains("TRANSACTION")) {
			result.addAll(transactionLogRepository.findAll().stream()
				.filter(log -> matchesFilter(log.getTraceId(), null, null, log.getCreatedAt(), filter))
				.map(UnifiedLogResponseDto::fromTransactionLog).toList());
		}

		result.sort(Comparator.comparing(UnifiedLogResponseDto::timestamp).reversed());

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), result.size());
		List<UnifiedLogResponseDto> pageContent = result.subList(start, end);
		return new PageImpl<>(pageContent, pageable, result.size());
	}

	private boolean matchesFilter(String traceId, Long userId, Long merchantId, LocalDateTime timestamp, UnifiedLogFilterRequest filter) {
		boolean match = true;
		if (filter.traceId() != null) match &= filter.traceId().equals(traceId);
		if (filter.userId() != null) match &= filter.userId().equals(userId);
		if (filter.merchantId() != null) match &= filter.merchantId().equals(merchantId);

		if (filter.from() != null) {
			LocalDateTime fromDateTime = filter.from().atStartOfDay();
			match &= !timestamp.isBefore(fromDateTime);
		}

		if (filter.to() != null) {
			LocalDateTime toDateTime = filter.to().plusDays(1).atStartOfDay().minusNanos(1);
			match &= !timestamp.isAfter(toDateTime);
		}

		return match;
	}

}
