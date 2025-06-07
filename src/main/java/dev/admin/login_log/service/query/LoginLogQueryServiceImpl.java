package dev.admin.login_log.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.login_log.dto.request.LoginLogSearchDto;
import dev.admin.login_log.dto.response.LoginLogResponseDto;
import dev.admin.login_log.dto.response.LoginLogSimpleResponseDto;
import dev.admin.login_log.entity.LoginLog;
import dev.admin.login_log.repository.LoginLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginLogQueryServiceImpl implements LoginLogQueryService {

    private final LoginLogRepository repository;

    @Override
    public LoginLogResponseDto getById(Long id) {
        LoginLog log = repository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_LOG_NOT_FOUND));
        return LoginLogResponseDto.from(log);
    }

    @Override
    public Page<LoginLogSimpleResponseDto> search(LoginLogSearchDto condition, Pageable pageable) {
        if (pageable.getPageNumber() < 0) {
            throw new GeneralException(ErrorStatus.INVALID_PAGE_NUMBER);
        }
        return repository.searchByCondition(
                condition.userId(),
                condition.merchantId(),
                condition.success(),
                condition.event(),
                pageable).map(LoginLogSimpleResponseDto::from);
    }
}
