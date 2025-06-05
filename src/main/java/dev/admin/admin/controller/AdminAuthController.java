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

import org.springframework.beans.factory.annotation.Value; // 이 import가 필요합니다.
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class AdminAuthController {

	private final AdminAuthCommandService authCommandService;
	private final JwtUtil jwtUtil;

	@Value("${app.cookie.domain}") // 설정 파일에서 app.cookie.domain 값을 읽어옵니다.
	private String cookieDomain;

	@PostMapping("/login")
	public ApiResponse<JwtDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
		JwtDto tokenDto = authCommandService.login(requestDto);

		ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
			.httpOnly(true)
			.secure(true)
			.path("/")
			.domain(cookieDomain) // 설정 파일에서 읽어온 도메인 값을 설정합니다.
			.sameSite("None")
			.maxAge(60 * 60)
			.build();

		ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
			.httpOnly(true)
			.secure(true)
			.path("/")
			.domain(cookieDomain) // refreshToken도 동일하게 설정
			.sameSite("None")
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
			.secure(true)
			.path("/")
			.domain(cookieDomain) // 로그아웃 시에도 도메인을 지정해야 삭제가 정확히 됩니다.
			.sameSite("None")
			.maxAge(0)
			.build();

		ResponseCookie clearRefreshToken = ResponseCookie.from("refreshToken", "")
			.httpOnly(true)
			.secure(true)
			.path("/")
			.domain(cookieDomain) // 로그아웃 시에도 도메인을 지정해야 삭제가 정확히 됩니다.
			.sameSite("None")
			.maxAge(0)
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
