package dev.admin.login_log.repository;

import dev.admin.login_log.entity.LoginLog;
import dev.admin.login_log.enums.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {

    @Query("""
        SELECT l FROM LoginLog l
        WHERE (:userId IS NULL OR l.userId = :userId)
          AND (:merchantId IS NULL OR l.merchantId = :merchantId)
          AND (:success IS NULL OR l.success = :success)
          AND (:event IS NULL OR l.event = :event)
    """)
    Page<LoginLog> searchByCondition(@Param("userId") Long userId,
                                     @Param("merchantId") Long merchantId,
                                     @Param("success") Boolean success,
                                     @Param("event") Event event,
                                     Pageable pageable);
}
