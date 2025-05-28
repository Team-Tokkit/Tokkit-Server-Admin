package dev.admin.unified_logs.service.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.admin.unified_logs.dto.request.UnifiedLogFilterRequest;
import dev.admin.unified_logs.dto.response.UnifiedLogResponseDto;

public interface UnifiedLogQueryService {
	Page<UnifiedLogResponseDto> getLogs(UnifiedLogFilterRequest filter, Pageable pageable);
}
