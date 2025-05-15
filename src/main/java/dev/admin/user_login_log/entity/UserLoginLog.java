package dev.admin.user_login_log.entity;

import dev.admin.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserLoginLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Event event;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String userAgent;

    @Column(nullable = false)
    private String timestamp;

    @Column(nullable = false)
    private Boolean success;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String reason;

    public enum Event {
        LOGIN, LOGOUT
    }
}
