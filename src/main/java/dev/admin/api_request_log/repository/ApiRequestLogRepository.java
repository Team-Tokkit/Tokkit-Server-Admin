package dev.admin.api_request_log.repository;

import dev.admin.api_request_log.dto.response.ApiRequestLogChartResponseDto;
import dev.admin.api_request_log.entity.ApiRequestLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiRequestLogRepository extends JpaRepository<ApiRequestLog, Long> {
    @Query("""
            SELECT new dev.admin.api_request_log.dto.response.ApiRequestLogChartResponseDto(
                CONCAT(r.method, ' ', r.endpoint),
                COUNT(r),
                AVG(r.responseTimeMs)
            )
            FROM ApiRequestLog r
            WHERE r.timestamp BETWEEN :start AND :end
              AND (:method IS NULL OR r.method = :method)
              AND (:statusDivided IS NULL OR FLOOR(r.responseStatus / 100) = :statusDivided)
              AND (:keyword IS NULL OR r.endpoint LIKE CONCAT('%', :keyword, '%'))
            GROUP BY r.method, r.endpoint
            ORDER BY AVG(r.responseTimeMs) DESC
            """)
    List<ApiRequestLogChartResponseDto> findGroupedStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("method") String method,
            @Param("statusDivided") Integer statusDivided,
            @Param("keyword") String keyword
    );


    @Query("""
            SELECT r
            FROM ApiRequestLog r
            WHERE r.timestamp BETWEEN :start AND :end
              AND (:method IS NULL OR r.method = :method)
              AND (:statusDivided IS NULL OR FLOOR(r.responseStatus / 100) = :statusDivided)
              AND (:keyword IS NULL OR r.endpoint LIKE CONCAT('%', :keyword, '%'))
            ORDER BY r.timestamp DESC
            """)
    Page<ApiRequestLog> findAllWithFilters(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("method") String method,
            @Param("statusDivided") Integer statusDivided,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
