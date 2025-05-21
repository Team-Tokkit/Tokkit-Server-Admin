package dev.admin.system_error_log.service.query;


import dev.admin.system_error_log.dto.request.SystemErrorLogSearchDto;
import dev.admin.system_error_log.dto.response.SystemErrorLogResponseDto;
import dev.admin.system_error_log.dto.response.SystemErrorLogSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SystemErrorLogQueryService {
    SystemErrorLogResponseDto getById(Long id);
    Page<SystemErrorLogSimpleResponseDto> search(SystemErrorLogSearchDto condition, Pageable pageable);

}
