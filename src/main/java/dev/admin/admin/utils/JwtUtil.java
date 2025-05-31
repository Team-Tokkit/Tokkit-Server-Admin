package dev.admin.admin.utils;

import dev.admin.admin.dto.response.JwtPayload;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.token.access-expiration-time}")
    private long accessTokenExpiration;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long refreshTokenExpiration;

    private Key key;

    /**
     * Bean 생성 직후 실행되어, 문자열 시크릿 키를 HMAC 키 객체로 변환
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Access Token 생성
     */
    public String generateAccessToken(String id, String email, String name, String role) {
        return Jwts.builder()
                .setSubject(id) // sub = 사용자 id
                .claim("email", email)
                .claim("name", name)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Refresh Token 생성
     */
    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 사용자 이메일(subject) 추출
     */
    public String getEmailFromToken(String token) {
        return parseClaims(token).get("email", String.class);
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("토큰 만료");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 토큰");
        } catch (MalformedJwtException e) {
            System.out.println("잘못된 형식의 토큰");
        } catch (SecurityException | IllegalArgumentException e) {
            System.out.println("토큰 검증 실패");
        }
        return false;
    }

    /**
     * 토큰의 Claims (내부 정보)를 꺼내는 내부 메서드
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtPayload parseToken(String token) {
        Claims claims = parseClaims(token);
        return new JwtPayload(
                claims.getSubject(), // id
                (String) claims.get("email"),
                (String) claims.get("name"),
                (String) claims.get("role")
        );
    }

}