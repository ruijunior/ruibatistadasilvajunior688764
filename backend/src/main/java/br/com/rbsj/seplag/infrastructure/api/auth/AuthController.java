package br.com.rbsj.seplag.infrastructure.api.auth;

import br.com.rbsj.seplag.infrastructure.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Value("${jwt.expiration-ms}")
    private long jwtExpiration;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        log.info("Tentativa de login: username={}", request.username());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        var userDetails = userDetailsService.loadUserByUsername(request.username());
        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        
        var response = LoginResponse.from(accessToken, refreshToken, jwtExpiration);

        log.info("Login realizado com sucesso: username={}", request.username());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {

        log.debug("Tentativa de refresh token");
        String username = jwtService.extractUsername(request.refreshToken());
        
        if (username != null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtService.isTokenValid(request.refreshToken(), userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                var refreshToken = jwtService.generateRefreshToken(userDetails); 
                
                var response = LoginResponse.from(accessToken, refreshToken, jwtExpiration);

                log.info("Refresh token realizado com sucesso: username={}", username);
                return ResponseEntity.ok(response);
            }
        }
        
        log.warn("Refresh token rejeitado: token inválido ou expirado");
        return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build();
    }
}
