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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));

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
        tokenRepository.save(admin.getEmail(), refreshToken);

        return new JwtDto(accessToken, refreshToken);
    }

    /**
     * 로그아웃: refreshToken 삭제
     */
    public void logout(String refreshToken) {
        // 저장소에 토큰이 존재할 때만 삭제
        if (tokenRepository.exists(refreshToken)) {
            tokenRepository.delete(refreshToken);
        }
    }

}
