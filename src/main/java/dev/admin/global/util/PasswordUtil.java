package dev.admin.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    // encoder를 매번 새로 만들지 않고 한 번만 재사용
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /** 비밀번호 암호화 */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /** 비밀번호 검증 */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
