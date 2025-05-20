package dev.admin.user.dto.response;

import dev.admin.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserSimpleResponseDto(
        @Schema(description = "사용자 ID", example = "1") Long id,
        @Schema(description = "이름", example = "홍길동") String name,
        @Schema(description = "이메일", example = "user1@mail.com") String email,
        @Schema(description = "전화번호", example = "010-1234-5678") String phoneNumber,
        @Schema(description = "상태", example = "false") Boolean isDormant,
        @Schema(description = "가입일", example = "2025-05-20") LocalDateTime createdAt
) {
    public static UserSimpleResponseDto of(User user) {
        return new UserSimpleResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getIsDormant(),
                user.getCreatedAt()
        );
    }
}
