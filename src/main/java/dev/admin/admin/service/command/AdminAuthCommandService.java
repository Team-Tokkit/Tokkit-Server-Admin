package dev.admin.admin.service.command;

import dev.admin.admin.dto.request.LoginRequestDto;
import dev.admin.admin.dto.response.JwtDto;
import dev.admin.admin.entity.Admin;
import dev.admin.admin.repository.AdminRepository;
import dev.admin.admin.repository.TokenRepository;
import dev.admin.admin.utils.JwtUtil;
import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthCommandService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    public JwtDto login(LoginRequestDto requestDto) {
        Admin admin = adminRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new GeneralException(ErrorStatus.ADMIN_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getPassword(), admin.getPassword())) {
            throw new GeneralException(ErrorStatus.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.generateAccessToken(
                admin.getId().toString(),
                admin.getEmail(),
                admin.getName(),
                admin.getRole().name()
        );

        String refreshToken = jwtUtil.generateRefreshToken();

        tokenRepository.save(admin.getEmail() + "_refreshToken", refreshToken); // refreshToken 저장

        return new JwtDto(accessToken, refreshToken);
    }

    /**
     * 로그아웃: refreshToken 삭제
     */
    public void logout(String refreshToken) {
        // Redis에서 RefreshToken 삭제
        String redisRefreshKey = "refresh:" + refreshToken;
        if (tokenRepository.exists(redisRefreshKey)) {
            tokenRepository.delete(redisRefreshKey);
        }

        // 이메일 추출
        String email = jwtUtil.getEmailFromToken(refreshToken);

        // 그러나 원하면 Redis에서 직접 삭제할 수 있음 (Access Token을 Redis에 저장한 경우)
        String redisAccessKey = email + "_accessToken";
        if (tokenRepository.exists(redisAccessKey)) {
            tokenRepository.delete(redisAccessKey);  // Redis에서 AccessToken 삭제
        }
    }
}
