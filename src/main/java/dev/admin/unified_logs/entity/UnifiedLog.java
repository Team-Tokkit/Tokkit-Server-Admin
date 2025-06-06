package dev.admin.unified_logs.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UnifiedLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String logType;
	private String traceId;
	private Long userId;
	private Long merchantId;
	private LocalDateTime timestamp;
	@Column(columnDefinition = "TEXT")
	private String summary;
	@Lob
	@Column(columnDefinition = "LONGTEXT")
	private String detail;
	private String statusOrSeverity;
}
