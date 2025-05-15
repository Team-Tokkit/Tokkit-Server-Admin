package dev.admin.user_login_log.repository;

import dev.admin.user_login_log.entity.UserLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, Long> {
}
