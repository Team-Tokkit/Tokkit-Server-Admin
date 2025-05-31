package dev.admin.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtPayload {
    private String sub;     // subject (adminId)
    private String email;
    private String name;
    private String role;
}
