package dev.admin.api_request_log.service.query;

import dev.admin.api_request_log.dto.response.ApiRequestLogChartResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogDetailResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogPageItemDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogPageResponseDto;
import dev.admin.api_request_log.repository.ApiRequestLogRepository;
import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiRequestLogQueryServiceImpl implements ApiRequestLogQueryService {

    private final ApiRequestLogRepository logRepository;

    @Override
    public List<ApiRequestLogChartResponseDto> getChartData(String startDate, String endDate, String method, Integer status, String keyword) {

        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        DateTimeUtil.validateStartBeforeEnd(start, end);
        return logRepository.findGroupedStats(start, end, method, status / 100, keyword);
    }

    @Override
    public ApiRequestLogPageResponseDto getPaginatedLogs(
            String startDate, String endDate, String method,
            Integer status, String keyword, Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        DateTimeUtil.validateStartBeforeEnd(start, end);
        System.out.println("------------------------------"+status / 100);
        var page = logRepository.findAllWithFilters(start, end, method, status / 100, keyword, pageable)
                .map(log -> new ApiRequestLogPageItemDto(
                        log.getId(),
                        log.getEndpoint(),
                        log.getMethod(),
                        log.getResponseStatus(),
                        log.getResponseTimeMs(),
                        log.getUserId(),
                        log.getTimestamp()
                ));

        return ApiRequestLogPageResponseDto.from(page);
    }

    @Override
    public ApiRequestLogDetailResponseDto getLogDetailById(Long id) {
        if (id == null) {
            throw new GeneralException(ErrorStatus.API_REQUEST_LOG_NOT_FOUND);
        }
        return ApiRequestLogDetailResponseDto.of(
                logRepository.findById(id)
                        .orElseThrow(() -> new GeneralException(ErrorStatus.API_REQUEST_LOG_NOT_FOUND))
        );
    }
}
