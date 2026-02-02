package br.com.rbsj.seplag.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artistas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Artista extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoArtista tipo;

    @ManyToMany
    @JoinTable(
        name = "artista_album",
        joinColumns = @JoinColumn(name = "artista_id"),
        inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private Set<Album> albuns = new HashSet<>();

    @Override
    public void validate() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do artista é obrigatório");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo do artista é obrigatório");
        }
    }

    public enum TipoArtista {
        CANTOR,
        BANDA
    }
}
