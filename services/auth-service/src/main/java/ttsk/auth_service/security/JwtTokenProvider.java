package ttsk.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ttsk.auth_service.dto.TokenResponse;
import ttsk.auth_service.exception.AccessDeniedException;
import ttsk.auth_service.model.Customer;
import ttsk.auth_service.model.Role;
import ttsk.auth_service.service.impl.CustomerServiceImpl;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final CustomerServiceImpl customerServiceImpl;
    private Key key;

    @Autowired
    public JwtTokenProvider(JwtProperties jwtProperties, UserDetailsService userDetailsService, CustomerServiceImpl customerServiceImpl) {
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
        this.customerServiceImpl = customerServiceImpl;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(UUID userId, String username, Set<Role> roles) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", userId.toString())
                .add("roles", resolveRoles(roles))
                .build();
        Instant validity = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.MINUTES);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(UUID userId, String username) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", userId.toString())
                .build();
        Instant validity = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public TokenResponse refreshUserTokens(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException("Access denied - invalid token");
        }
        UUID userId = getId(refreshToken);
        Customer customer = customerServiceImpl.getById(userId);
        return new TokenResponse(
                userId,
                customer.getUsername(),
                createAccessToken(userId, customer.getUsername(), customer.getRoles()),
                createRefreshToken(userId, customer.getUsername())
        );
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private UUID getId(String token) {
        String id = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", String.class);

        if (id == null) {
            throw new IllegalArgumentException(" ID is missing in token");
        }
        return UUID.fromString(id);
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
