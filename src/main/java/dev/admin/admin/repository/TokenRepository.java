package dev.admin.admin.repository;

public interface TokenRepository {
    void save(String email, String refreshToken);
    boolean exists(String refreshToken);
    void delete(String refreshToken);
    String findByEmail(String email); // 로그인 시에도 사용되므로 유지
}
