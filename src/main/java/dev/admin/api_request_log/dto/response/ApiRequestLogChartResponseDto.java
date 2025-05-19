package dev.admin.api_request_log.dto.response;

public record ApiRequestLogChartResponseDto(
        String label,
        Long count,
        Double avgResponseTime
) {
    public ApiRequestLogChartResponseDto(String label, Long count, Double avgResponseTime) {
        this.label = label;
        this.count = count;
        this.avgResponseTime = avgResponseTime;
    }
}