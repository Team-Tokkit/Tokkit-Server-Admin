package dev.admin.api_request_log.repository;

import dev.admin.api_request_log.entity.ApiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRequestLogRepository extends JpaRepository<ApiRequestLog,Long> {
}
