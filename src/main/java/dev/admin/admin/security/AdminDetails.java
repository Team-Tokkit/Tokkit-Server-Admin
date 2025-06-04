package dev.admin.admin.security;

import java.util.List;
import dev.admin.admin.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class AdminDetails implements UserDetails {

    // 실제 DB에서 조회된 Admin 객체를 담음
    private final Admin admin;

    /**
     * 관리자 권한 반환
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    /**
     * 로그인에 사용할 비밀번호 반환
     */
    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    /**
     * 로그인에 사용할 ID(email) 반환
     */
    @Override
    public String getUsername() {
        return admin.getEmail();
    }

    /**
     * 계정 만료 여부 (true = 만료되지 않음)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부 (true = 잠기지 않음)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명(비밀번호) 만료 여부 (true = 만료되지 않음)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정 활성화 여부 (true = 활성 계정)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 인증이 완료된 관리자의 엔티티 객체를 외부에서 사용할 수 있도록 반환
     */
    public Admin getAdmin() {
        return this.admin;
    }
}
