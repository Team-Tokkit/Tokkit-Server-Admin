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
import dev.admin.unified_logs.entity.UnifiedLog;
import dev.admin.unified_logs.repository.UnifiedLogRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnifiedLogQueryServiceImpl implements UnifiedLogQueryService {

	private final UnifiedLogRepository unifiedLogRepository;

	@Override
	public Page<UnifiedLogResponseDto> getLogs(UnifiedLogFilterRequest filter, Pageable pageable) {
		if (filter.traceId() != null) {
			return unifiedLogRepository.findByTraceId(filter.traceId(), pageable)
					.map(UnifiedLogResponseDto::from);
		}
		return unifiedLogRepository.findAll(pageable)
				.map(UnifiedLogResponseDto::from);
	}

	private boolean matchesFilter(UnifiedLog log, UnifiedLogFilterRequest filter) {
		if (filter.traceId() != null && !filter.traceId().equals(log.getTraceId()))
			return false;
		if (filter.userId() != null && !filter.userId().equals(log.getUserId()))
			return false;
		if (filter.merchantId() != null && !filter.merchantId().equals(log.getMerchantId()))
			return false;

		if (filter.from() != null && log.getTimestamp().isBefore(filter.from().atStartOfDay()))
			return false;
		if (filter.to() != null && log.getTimestamp().isAfter(filter.to().plusDays(1).atStartOfDay().minusNanos(1)))
			return false;

		if (filter.logTypes() != null && !filter.logTypes().contains(log.getLogType()))
			return false;

		return true;
	}
}