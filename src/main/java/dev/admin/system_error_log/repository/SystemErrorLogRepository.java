package dev.admin.system_error_log.repository;

import dev.admin.system_error_log.entity.SystemErrorLog;
import dev.admin.system_error_log.enums.Severity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SystemErrorLogRepository extends JpaRepository<SystemErrorLog, Long> {
    @Query("""
            SELECT l FROM SystemErrorLog l
            WHERE (:keyword IS NULL OR l.endpoint LIKE %:keyword%)
              AND (:severity IS NULL OR l.severity = :severity)
                         ORDER BY l.createdAt DESC
            """)
    Page<SystemErrorLog> searchByCondition(@Param("keyword") String keyword,
                                           @Param("severity") Severity severity,
                                           Pageable pageable);
}
