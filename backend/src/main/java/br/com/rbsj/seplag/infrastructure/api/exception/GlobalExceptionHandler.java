package br.com.rbsj.seplag.infrastructure.api.exception;

import br.com.rbsj.seplag.domain.validation.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        String msg = ex.getMessage() != null ? ex.getMessage() : "Requisição inválida";

        boolean notFound = msg.toLowerCase().contains("não encontrado")
                || msg.toLowerCase().contains("not found")
                || msg.toLowerCase().contains("não encontrada");

        if (notFound) {
            log.warn("Recurso não encontrado: path={} message={}", path, msg);
            ApiError body = ApiError.of(HttpStatus.NOT_FOUND.value(), "Not Found", msg, path);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        log.warn("Argumento inválido: path={} message={}", path, msg);
        ApiError body = ApiError.of(HttpStatus.BAD_REQUEST.value(), "Bad Request", msg, path);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(
            DomainException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        List<String> details = ex.getErrors().stream()
                .map(e -> e.message())
                .toList();
        String message = details.isEmpty() ? "Erro de validação" : details.get(0);

        log.warn("Erro de domínio/validação: path={} details={}", path, details);
        ApiError body = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                path,
                details.size() > 1 ? details : null
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFound(
            UsernameNotFoundException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        log.warn("Usuário não encontrado no login: path={} username={}", path, ex.getMessage());
        ApiError body = ApiError.of(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Credenciais inválidas",
                path
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            org.springframework.security.core.AuthenticationException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        log.warn("Falha de autenticação: path={} message={}", path, ex.getMessage());
        ApiError body = ApiError.of(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Credenciais inválidas",
                path
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        String msg = ex.getMessage() != null ? ex.getMessage() : "Erro interno";

        boolean storageError = msg.contains("MinIO") || msg.contains("presigned") || msg.contains("storage");
        HttpStatus status = storageError ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("Erro durante processamento: path={} message={}", path, msg, ex);
        ApiError body = ApiError.of(
                status.value(),
                status.getReasonPhrase(),
                storageError ? "Serviço de armazenamento temporariamente indisponível" : "Erro interno do servidor",
                path
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        log.error("Erro inesperado: path={}", path, ex);
        ApiError body = ApiError.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Erro interno do servidor",
                path
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
