package ttsk.api_gateway.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ttsk.api_gateway.exception.UnauthorizedException;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handlerUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
}
