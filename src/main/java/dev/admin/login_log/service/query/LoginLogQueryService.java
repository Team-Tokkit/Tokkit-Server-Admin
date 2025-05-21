package dev.admin.login_log.service.query;

import dev.admin.login_log.dto.request.LoginLogSearchDto;
import dev.admin.login_log.dto.response.LoginLogResponseDto;
import dev.admin.login_log.dto.response.LoginLogSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoginLogQueryService {
    LoginLogResponseDto getById(Long id);
    Page<LoginLogSimpleResponseDto> search(LoginLogSearchDto condition, Pageable pageable);
}
