package dev.admin.system_error_log.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.system_error_log.dto.request.SystemErrorLogSearchDto;
import dev.admin.system_error_log.dto.response.SystemErrorLogResponseDto;
import dev.admin.system_error_log.dto.response.SystemErrorLogSimpleResponseDto;
import dev.admin.system_error_log.entity.SystemErrorLog;
import dev.admin.system_error_log.repository.SystemErrorLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemErrorLogQueryServiceImpl implements SystemErrorLogQueryService {

    private final SystemErrorLogRepository repository;

    @Override
    public SystemErrorLogResponseDto getById(Long id) {

        SystemErrorLog log = repository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SYSTEM_ERROR_LOG_NOT_FOUND));
        return SystemErrorLogResponseDto.from(log);
    }

    @Override
    public Page<SystemErrorLogSimpleResponseDto> search(SystemErrorLogSearchDto condition, Pageable pageable) {
        return repository.searchByCondition(
                condition.keyword(),
                condition.severity(),
                pageable).map(SystemErrorLogSimpleResponseDto::from);
    }
}
