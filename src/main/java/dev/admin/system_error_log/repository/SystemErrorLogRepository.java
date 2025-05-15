package dev.admin.system_error_log.repository;

import dev.admin.system_error_log.entity.SystemErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemErrorLogRepository extends JpaRepository<SystemErrorLog, Long> {
}
