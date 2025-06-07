package dev.admin.unified_logs.repository;

import dev.admin.unified_logs.entity.UnifiedLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UnifiedLogRepository extends JpaRepository<UnifiedLog, Long> {
    @Query("""
                SELECT l FROM UnifiedLog l
                WHERE (:traceId IS NULL OR l.traceId = :traceId)
                  AND (:userId IS NULL OR l.userId = :userId)
                  AND (:merchantId IS NULL OR l.merchantId = :merchantId)
                  AND (:logTypes IS NULL OR l.logType IN :logTypes)
                  AND (:from IS NULL OR l.timestamp >= :from)
                  AND (:to IS NULL OR l.timestamp < :to)
                ORDER BY l.timestamp DESC
            """)
    Page<UnifiedLog> findAllWithFilters(
            @Param("traceId") String traceId,
            @Param("userId") Long userId,
            @Param("merchantId") Long merchantId,
            @Param("logTypes") List<String> logTypes,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable);

    @Query("SELECT l FROM UnifiedLog l WHERE l.traceId = :traceId")
    Page<UnifiedLog> findByTraceId(@Param("traceId") String traceId, Pageable pageable);
}
