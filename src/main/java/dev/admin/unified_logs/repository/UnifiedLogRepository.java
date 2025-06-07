package dev.admin.unified_logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.admin.unified_logs.entity.UnifiedLog;

public interface UnifiedLogRepository extends JpaRepository<UnifiedLog, Long> {
}
