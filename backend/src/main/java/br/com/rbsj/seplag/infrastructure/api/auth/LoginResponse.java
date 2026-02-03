package br.com.rbsj.seplag.infrastructure.api.auth;

public record LoginResponse(
        String token,
        String type,
        long expiresIn
) {
    public static LoginResponse from(String token, long expiresInMs) {
        return new LoginResponse(token, "Bearer", expiresInMs);
    }
}
