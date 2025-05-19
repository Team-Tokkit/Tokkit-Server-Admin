package dev.admin.login_log.repository;

import dev.admin.login_log.entity.UserLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, Long> {
}
