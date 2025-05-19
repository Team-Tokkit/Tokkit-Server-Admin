package dev.admin.api_request_log.dto.response;

import dev.admin.api_request_log.entity.ApiRequestLog;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Schema(description = "단건 API 요청 로그 응답 DTO")
public record ApiRequestLogDetailResponseDto(

        @Schema(description = "로그 ID", example = "1021")
        @NotNull Long id,

        @Schema(description = "HTTP 메서드", example = "POST")
        @NotNull String method,

        @Schema(description = "요청 엔드포인트", example = "/admin-api/login")
        @NotNull String endpoint,

        @Schema(description = "HTTP 상태 코드", example = "200")
        @PositiveOrZero int status,

        @Schema(description = "요청한 사용자 ID", example = "2")
        Long userId,

        @Schema(description = "요청자 IP 주소", example = "192.168.0.1")
        String ipAddress,

        @Schema(description = "요청 바디", example = "{ \"email\": \"user@tokkit.com\" }")
        String requestBody,

        @Schema(description = "쿼리 파라미터 문자열", example = "page=1&size=10")
        String queryParams,

        @Schema(description = "응답 시간 (ms)", example = "250")
        @PositiveOrZero int responseTimeMs,

        @Schema(description = "Trace ID", example = "abc-1234-def")
        String traceId,

        @Schema(description = "요청 시각", example = "2024-05-15T05:16:00")
        @NotNull LocalDateTime timestamp

) {
        public static ApiRequestLogDetailResponseDto of(ApiRequestLog log) {
                return new ApiRequestLogDetailResponseDto(
                        log.getId(),
                        log.getMethod(),
                        log.getEndpoint(),
                        log.getResponseStatus(),
                        log.getUserId(),
                        log.getIpAddress(),
                        log.getRequestBody(),
                        log.getQueryParams(),
                        log.getResponseTimeMs(),
                        log.getTraceId(),
                        log.getTimestamp()
                );
        }

}
