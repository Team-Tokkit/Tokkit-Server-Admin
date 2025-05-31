package dev.admin.admin.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTokenRepository implements TokenRepository {

    // 메모리에 refresh token 저장 (email -> token)
    private final Map<String, String> store = new ConcurrentHashMap<>();

    @Override
    public void save(String email, String refreshToken) {
        store.put(email, refreshToken);
    }

    @Override
    public boolean exists(String refreshToken) {
        return store.containsValue(refreshToken);
    }

    @Override
    public void delete(String refreshToken) {
        store.entrySet().removeIf(entry -> entry.getValue().equals(refreshToken));
    }

    @Override
    public String findByEmail(String email) {
        return store.get(email);
    }
}
