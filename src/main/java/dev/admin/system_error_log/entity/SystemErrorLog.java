package dev.admin.system_error_log.entity;

import dev.admin.global.entity.BaseTimeEntity;
import dev.admin.system_error_log.enums.Severity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SystemErrorLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String endpoint;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(columnDefinition = "LONGTEXT")
    private String stackTrace;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 50)
    private String serverName;

    @Column(nullable = false)
    private String traceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Severity severity;
}
