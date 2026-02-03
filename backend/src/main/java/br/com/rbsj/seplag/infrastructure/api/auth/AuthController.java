package br.com.rbsj.seplag.infrastructure.api.auth;

import br.com.rbsj.seplag.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        var userDetails = userDetailsService.loadUserByUsername(request.username());
        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        
        var response = LoginResponse.from(accessToken, refreshToken, jwtExpiration);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {
        String username = jwtService.extractUsername(request.refreshToken());
        
        if (username != null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtService.isTokenValid(request.refreshToken(), userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                var refreshToken = jwtService.generateRefreshToken(userDetails); 
                
                var response = LoginResponse.from(accessToken, refreshToken, jwtExpiration);
                return ResponseEntity.ok(response);
            }
        }
        
        return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build();
    }
}
