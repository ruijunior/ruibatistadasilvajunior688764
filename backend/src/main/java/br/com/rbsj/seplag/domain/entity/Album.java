package br.com.rbsj.seplag.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "albuns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Album extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    @Column(name = "url_capa")
    private String urlCapa;

    @ManyToMany(mappedBy = "albuns")
    private Set<Artista> artistas = new HashSet<>();

    @Override
    public void validate() {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título do álbum é obrigatório");
        }
    }
}
