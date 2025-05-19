package dev.admin.api_request_log.controller;

import dev.admin.api_request_log.dto.response.ApiRequestLogChartResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogDetailResponseDto;
import dev.admin.api_request_log.dto.response.ApiRequestLogPageResponseDto;
import dev.admin.api_request_log.service.query.ApiRequestLogQueryService;
import dev.admin.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-api/logs/api-requests")
@RequiredArgsConstructor
public class ApiRequestLogController {

    private final ApiRequestLogQueryService apiRequestLogQueryService;

    @Operation(summary = "API 요청 로그 차트 통계 조회", description = "기간, 메서드, 상태코드, 키워드를 기준으로 통계 데이터를 조회합니다.")
    @GetMapping("/chart")
    public ApiResponse<List<ApiRequestLogChartResponseDto>> getChart(
            @Parameter(description = "시작 날짜 (yyyy-MM-dd 형식)", example = "2025-05-01")
            @RequestParam String startDate,

            @Parameter(description = "종료 날짜 (yyyy-MM-dd 형식)", example = "2025-05-18")
            @RequestParam String endDate,

            @Parameter(description = "HTTP 메서드 (GET, POST 등)", example = "GET")
            @RequestParam(required = false) String method,

            @Parameter(description = "HTTP 응답 상태 코드", example = "200")
            @RequestParam(required = false) Integer status,

            @Parameter(description = "API 엔드포인트 키워드 (포함 검색)", example = "/api/wallet")
            @RequestParam(required = false) String keyword
    ) {
        List<ApiRequestLogChartResponseDto> response =
                apiRequestLogQueryService.getChartData(startDate, endDate, method, status, keyword);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "API 요청 로그 목록 조회 (페이징)", description = "조건에 따라 필터링된 API 요청 로그 목록을 페이징 형태로 조회합니다.")
    @GetMapping
    public ApiResponse<ApiRequestLogPageResponseDto> getLogs(
            @Parameter(description = "시작 날짜 (yyyy-MM-dd 형식)", example = "2025-05-01")
            @RequestParam String startDate,

            @Parameter(description = "종료 날짜 (yyyy-MM-dd 형식)", example = "2025-05-18")
            @RequestParam String endDate,

            @Parameter(description = "HTTP 메서드 (GET, POST 등)", example = "GET")
            @RequestParam(required = false) String method,

            @Parameter(description = "HTTP 응답 상태 코드", example = "200")
            @RequestParam(required = false) Integer status,

            @Parameter(description = "API 엔드포인트 키워드 (포함 검색)", example = "/api/wallet")
            @RequestParam(required = false) String keyword,

            @Parameter(description = "페이지 및 정렬 정보")
            Pageable pageable
    ) {
        ApiRequestLogPageResponseDto response = apiRequestLogQueryService.getPaginatedLogs(startDate, endDate, method, status, keyword, pageable);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "단일 API 요청 로그 상세 조회", description = "ID를 기준으로 API 요청 로그의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponse<ApiRequestLogDetailResponseDto> getLogDetail(
            @Parameter(description = "로그 ID", example = "1")
            @PathVariable Long id
    ) {
        ApiRequestLogDetailResponseDto response = apiRequestLogQueryService.getLogDetailById(id);
        return ApiResponse.onSuccess(response);
    }
}
