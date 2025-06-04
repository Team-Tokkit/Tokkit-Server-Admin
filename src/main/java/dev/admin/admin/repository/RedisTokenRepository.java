package dev.admin.admin.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class RedisTokenRepository implements TokenRepository {

    private final StringRedisTemplate redis;

    @Override
    public void save(String key, String value) {
        log.info("🔥 RedisTokenRepository.save() 실행됨");
        log.info("📝 저장할 key = {}", key);
        log.info("📝 저장할 value = {}", value);

        // ⏱️ 30분 TTL 추가 (설정 안 하면 expire 안 됨)
        redis.opsForValue().set(key, value, Duration.ofMinutes(30));
    }

    @Override
    public Optional<String> find(String key) {
        return Optional.ofNullable(redis.opsForValue().get(key));
    }

    @Override
    public void delete(String key) {
        redis.delete(key);
    }

    @Override
    public boolean exists(String key) {
        Boolean result = redis.hasKey(key);
        return result != null && result;
    }
}

