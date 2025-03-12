package ttsk.order_service.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String accessToken;
    private final String refreshToken;

    public JwtAuthenticationToken(String accessToken, String refreshToken, List<String> roles) {
        super(toGrantedAuthorities(roles));
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    private static Collection<? extends GrantedAuthority> toGrantedAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}
