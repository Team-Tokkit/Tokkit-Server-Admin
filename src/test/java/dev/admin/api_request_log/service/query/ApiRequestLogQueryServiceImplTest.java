package dev.admin.api_request_log.service.query;

import dev.admin.api_request_log.dto.response.ApiRequestLogChartResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogDetailResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogPageItemDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogPageResponseDto;
import dev.admin.api_request_log.entity.ApiRequestLog;
import dev.admin.api_request_log.repository.ApiRequestLogRepository;
import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ApiRequestLogQueryServiceImplTest {

    @Mock
    private ApiRequestLogRepository logRepository;

    @InjectMocks
    private ApiRequestLogQueryServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("차트 데이터: method, status, keyword로 필터링된 통계 응답 확인")
    void getChartData_shouldReturnFilteredStats() {
        // given
        var chartResponse = List.of(new ApiRequestLogChartResponseDto("GET /api/users", 5L, 123.4));
        when(logRepository.findGroupedStats(any(), any(), any(), any(), any())).thenReturn(chartResponse);

        // when
        var result = service.getChartData("2025-05-01", "2025-05-03", "GET", 200, "/api/users");

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).label()).contains("/api/users");
        assertThat(result.get(0).count()).isEqualTo(5);
    }

    @Test
    @DisplayName("차트 데이터: 잘못된 날짜 순서 예외 처리")
    void getChartData_invalidDate_shouldThrowException() {
        // when
        GeneralException exception = assertThrows(GeneralException.class, () ->
                service.getChartData("2025-05-03", "2025-05-01", null, null, null)
        );

        // then
        assertThat(exception.getErrorStatus()).isEqualTo(ErrorStatus.INVALID_DATE_FORMAT);
    }

    @Test
    @DisplayName("페이지 로그 데이터: 여러 필터 조건으로 조회")
    void getPaginatedLogs_shouldMapCorrectly() {
        // given
        var log = ApiRequestLog.builder()
                .id(1L)
                .method("POST")
                .endpoint("/api/logs")
                .responseStatus(200)
                .responseTimeMs(300)
                .userId(99L)
                .timestamp(LocalDateTime.of(2025, 5, 2, 10, 0))
                .build();

        Page<ApiRequestLog> page = new PageImpl<>(List.of(log));
        when(logRepository.findAllWithFilters(any(), any(), any(), any(), any(), any())).thenReturn(page);

        // when
        ApiRequestLogPageResponseDto result = service.getPaginatedLogs("2025-05-01", "2025-05-03", "POST", 200, "/api", PageRequest.of(0, 10));

        // then
        assertThat(result.content()).hasSize(1);
        ApiRequestLogPageItemDto item = result.content().get(0);
        assertThat(item.endpoint()).isEqualTo("/api/logs");
        assertThat(item.method()).isEqualTo("POST");
    }

    @Test
    @DisplayName("상세 로그 조회: 존재하는 ID 응답 확인")
    void getLogDetailById_shouldReturnDto() {
        // given
        var log = ApiRequestLog.builder()
                .id(1L)
                .method("GET")
                .endpoint("/api/detail")
                .responseStatus(200)
                .userId(1L)
                .responseTimeMs(180)
                .traceId("abc-123")
                .timestamp(LocalDateTime.now())
                .build();

        when(logRepository.findById(eq(1L))).thenReturn(Optional.of(log));

        // when
        ApiRequestLogDetailResponseDto dto = service.getLogDetailById(1L);

        // then
        assertThat(dto.endpoint()).isEqualTo("/api/detail");
        assertThat(dto.method()).isEqualTo("GET");
        assertThat(dto.traceId()).isEqualTo("abc-123");
    }

    @Test
    @DisplayName("상세 로그 조회: 존재하지 않는 ID 예외 처리")
    void getLogDetailById_shouldThrowIfNotFound() {
        // given
        when(logRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        GeneralException exception = assertThrows(GeneralException.class, () -> service.getLogDetailById(9999L));

        // then
        assertThat(exception.getErrorStatus()).isEqualTo(ErrorStatus.API_REQUEST_LOG_NOT_FOUND);
    }
}
