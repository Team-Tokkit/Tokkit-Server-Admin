package dev.admin.admin.controller;

import dev.admin.admin.dto.request.LoginRequestDto;
import dev.admin.admin.dto.response.AdminInfoResponse;
import dev.admin.admin.dto.response.JwtDto;
import dev.admin.admin.dto.response.JwtPayload;
import dev.admin.admin.service.command.AdminLoginCommandService;
import dev.admin.admin.service.command.AdminLogoutCommandService;
import dev.admin.admin.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminLoginCommandService adminLoginCommandService;
    private final AdminLogoutCommandService adminLogoutCommandService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        JwtDto tokenDto = adminLoginCommandService.login(requestDto);

        // 쿠키 설정은 그대로 유지
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(60 * 60)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        // ✅ 응답 바디에 토큰도 포함
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refreshToken") String refreshToken,
                                       HttpServletResponse response) {
        // 서비스 호출: refreshToken 기반 로그아웃 처리 (DB/Redis 삭제 등)
        adminLogoutCommandService.logout(refreshToken);

        // accessToken & refreshToken 쿠키 삭제
        ResponseCookie clearAccessToken = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        ResponseCookie clearRefreshToken = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", clearAccessToken.toString());
        response.addHeader("Set-Cookie", clearRefreshToken.toString());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AdminInfoResponse> me(@CookieValue("accessToken") String accessToken) {
        JwtPayload payload = jwtUtil.parseToken(accessToken);
        return ResponseEntity.ok(
                new AdminInfoResponse(
                        payload.getSub(),
                        payload.getEmail(),
                        payload.getName(),
                        payload.getRole()
                )
        );
    }

}
