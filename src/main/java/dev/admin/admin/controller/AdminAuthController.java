package dev.admin.admin.controller;

import dev.admin.admin.dto.request.LoginRequestDto;
import dev.admin.admin.dto.response.AdminInfoResponse;
import dev.admin.admin.dto.response.JwtDto;
import dev.admin.admin.dto.response.JwtPayload;
import dev.admin.admin.service.command.AdminAuthCommandService;
import dev.admin.admin.utils.JwtUtil;
import dev.admin.global.apiPayload.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthCommandService authCommandService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ApiResponse<JwtDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        JwtDto tokenDto = authCommandService.login(requestDto);

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

        return ApiResponse.onSuccess(tokenDto);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@CookieValue("refreshToken") String refreshToken,
                                       HttpServletResponse response) {
        authCommandService.logout(refreshToken);

        ResponseCookie clearAccessToken = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        ResponseCookie clearRefreshToken = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", clearAccessToken.toString());
        response.addHeader("Set-Cookie", clearRefreshToken.toString());

        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/me")
    public ApiResponse<AdminInfoResponse> me(@CookieValue("accessToken") String accessToken) {
        JwtPayload payload = jwtUtil.parseToken(accessToken);

        AdminInfoResponse info = new AdminInfoResponse(
                payload.getSub(),
                payload.getEmail(),
                payload.getName(),
                payload.getRole()
        );

        return ApiResponse.onSuccess(info);
    }
}
