package dev.admin.admin.repository;

import java.util.Optional;

public interface TokenRepository {
    void save(String key, String value);
    Optional<String> find(String key);
    void delete(String key);
    boolean exists(String key);
}
