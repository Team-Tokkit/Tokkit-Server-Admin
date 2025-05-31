package dev.admin.admin.service.command;

import dev.admin.admin.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLogoutCommandService {

    private final TokenRepository tokenRepository;

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
