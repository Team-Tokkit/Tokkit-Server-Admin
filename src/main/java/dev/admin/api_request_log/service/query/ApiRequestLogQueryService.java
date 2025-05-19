package dev.admin.api_request_log.service.query;

import dev.admin.api_request_log.dto.response.ApiRequestLogChartResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogDetailResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogPageResponseDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface ApiRequestLogQueryService {

    List<ApiRequestLogChartResponseDto> getChartData(
            String startDate,
            String endDate,
            String method,
            Integer status,
            String keyword
    );

    ApiRequestLogPageResponseDto getPaginatedLogs(
            String startDate,
            String endDate,
            String method,
            Integer status,
            String keyword,
            Pageable pageable
    );

    ApiRequestLogDetailResponseDto getLogDetailById(Long id);
}
