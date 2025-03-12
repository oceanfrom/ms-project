package ttsk.auth_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttsk.auth_service.dto.LoginRequest;
import ttsk.auth_service.dto.TokenResponse;
import ttsk.auth_service.model.Customer;
import ttsk.auth_service.security.JwtTokenProvider;
import ttsk.auth_service.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerServiceImpl customerServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(CustomerServiceImpl customerServiceImpl, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.customerServiceImpl = customerServiceImpl;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        Customer customer = customerServiceImpl.getByUserName(loginRequest.username());
        return new TokenResponse(
                customer.getId(),
                customer.getUsername(),
                jwtTokenProvider.createAccessToken(customer.getId(), customer.getUsername(), customer.getRoles()),
                jwtTokenProvider.createRefreshToken(customer.getId(), customer.getUsername())
        );
    }

    @Override
    @Transactional
    public TokenResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);

    }
}
