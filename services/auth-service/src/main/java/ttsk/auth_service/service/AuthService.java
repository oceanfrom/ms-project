package ttsk.auth_service.service;

import ttsk.auth_service.dto.LoginRequest;
import ttsk.auth_service.dto.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest loginRequest);
    TokenResponse refresh(String refreshToken);
}
