package br.com.rbsj.seplag.infrastructure.album.persistence;

import br.com.rbsj.seplag.infrastructure.artista.persistence.ArtistaJpaEntity;
import br.com.rbsj.seplag.infrastructure.artista.persistence.TipoArtistaJpa;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AlbumSpecification {

    public static Specification<AlbumJpaEntity> withFilter(String term) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(term)) {
                return cb.conjunction();
            }

            String likeTerm = "%" + term.toLowerCase() + "%";

            var titlePredicate = cb.like(cb.lower(root.get("titulo")), likeTerm);

            var yearPredicate = cb.conjunction();
            try {
                int year = Integer.parseInt(term);
                yearPredicate = cb.equal(root.get("anoLancamento"), year);
            } catch (NumberFormatException ignored) {
                yearPredicate = cb.disjunction();
            }

            Join<AlbumJpaEntity, ArtistaJpaEntity> artistasJoin = root.join("artistas", JoinType.LEFT);
            var artistPredicate = cb.like(cb.lower(artistasJoin.get("nome")), likeTerm);

            query.distinct(true);

            return cb.or(titlePredicate, yearPredicate, artistPredicate);
        };
    }

    public static Specification<AlbumJpaEntity> withTipoArtista(String tipoArtista) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(tipoArtista)) {
                return cb.conjunction();
            }

            try {
                TipoArtistaJpa tipo = TipoArtistaJpa.valueOf(tipoArtista.toUpperCase());
                Join<AlbumJpaEntity, ArtistaJpaEntity> artistasJoin = root.join("artistas", JoinType.INNER);
                query.distinct(true);
                return cb.equal(artistasJoin.get("tipo"), tipo);
            } catch (IllegalArgumentException e) {
                return cb.conjunction();
            }
        };
    }
}
