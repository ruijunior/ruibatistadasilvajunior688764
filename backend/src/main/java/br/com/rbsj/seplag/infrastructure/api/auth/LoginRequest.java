package br.com.rbsj.seplag.infrastructure.api.auth;

public record LoginRequest(
        String username,
        String password
) {
}
