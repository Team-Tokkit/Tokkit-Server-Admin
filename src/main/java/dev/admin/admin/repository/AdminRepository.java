package dev.admin.admin.repository;

import dev.admin.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * 이메일로 관리자 정보 조회 (로그인용)
     */
    Optional<Admin> findByEmail(String email);
}
