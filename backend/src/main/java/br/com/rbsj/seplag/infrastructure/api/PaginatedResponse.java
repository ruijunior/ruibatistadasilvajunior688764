package br.com.rbsj.seplag.infrastructure.api;

import br.com.rbsj.seplag.domain.pagination.Pagination;

import java.util.List;

public record PaginatedResponse<T>(
        int currentPage,
        int perPage,
        long total,
        List<T> items
) {
    public static <T> PaginatedResponse<T> from(Pagination<T> pagination) {
        return new PaginatedResponse<>(
                pagination.currentPage(),
                pagination.perPage(),
                pagination.total(),
                pagination.items()
        );
    }
}
