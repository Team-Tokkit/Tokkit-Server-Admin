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
		Page<UnifiedLog> logs = unifiedLogRepository.findAll(pageable); // 필터링은 예시이며 조건에 따라 직접 JPQL 추가 가능
		List<UnifiedLogResponseDto> dtos = logs.stream()
				.filter(log -> matchesFilter(log, filter))
				.map(UnifiedLogResponseDto::from)
				.sorted(Comparator.comparing(UnifiedLogResponseDto::timestamp).reversed())
				.toList();

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), dtos.size());
		List<UnifiedLogResponseDto> pageContent = dtos.subList(start, end);
		return new PageImpl<>(pageContent, pageable, dtos.size());
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