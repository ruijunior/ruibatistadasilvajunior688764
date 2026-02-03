package br.com.rbsj.seplag.infrastructure.api.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String type,
        long expiresIn
) {
    public static LoginResponse from(String accessToken, String refreshToken, long expiresInMs) {
        return new LoginResponse(accessToken, refreshToken, "Bearer", expiresInMs);
    }
}
