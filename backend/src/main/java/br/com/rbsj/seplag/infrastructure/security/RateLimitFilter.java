package br.com.rbsj.seplag.infrastructure.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int RETRY_AFTER_SECONDS = 60;
    private static final List<String> SKIP_PATHS = List.of(
            "/api/v1/auth/",
            "/actuator/",
            "/api/swagger-ui",
            "/v1/api-docs",
            "/v3/api-docs",
            "/swagger-ui",
            "/error"
    );
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String USER = "user:";
    public static final String FORWARDED_FOR = "X-Forwarded-For";
    public static final String IP = "ip:";
    public static final String UTF_8 = "UTF-8";
    public static final String CONTENT_TYPE = "application/json";
    public static final String RETRY_AFTER = "Retry-After";

    @Value("${rate-limit.enabled:true}")
    private boolean enabled;

    @Value("${rate-limit.requests-per-minute:10}")
    private int requestsPerMinute;

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!enabled) {
            return true;
        }
        String path = request.getRequestURI();
        return SKIP_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String key = resolveKey(request);
        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            log.warn("Rate limit excedido: key={} path={}", key, request.getRequestURI());
            response.setStatus(429); // Too Many Requests
            response.setHeader(RETRY_AFTER, String.valueOf(RETRY_AFTER_SECONDS));
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(UTF_8);
            response.getWriter().write("{\"error\":\"Too Many Requests\",\"message\":\"Limite de " + requestsPerMinute + " requisições por minuto excedido.\"}");
        }
    }

    private String resolveKey(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !ANONYMOUS_USER.equals(auth.getPrincipal())) {
            return USER + auth.getName();
        }
        String forwarded = request.getHeader(FORWARDED_FOR);
        if (forwarded != null && !forwarded.isBlank()) {
            return IP + forwarded.split(",")[0].trim();
        }
        return IP + request.getRemoteAddr();
    }

    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(requestsPerMinute, Refill.greedy(requestsPerMinute, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
