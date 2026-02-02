package br.com.rbsj.seplag.infrastructure.artista.persistence;

import br.com.rbsj.seplag.infrastructure.persistence.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "artistas")
public class ArtistaJpaEntity extends AuditableEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    private TipoArtistaJpa tipo;

    public ArtistaJpaEntity() {
    }

    public ArtistaJpaEntity(
            String id,
            String nome,
            TipoArtistaJpa tipo
    ) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoArtistaJpa getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtistaJpa tipo) {
        this.tipo = tipo;
    }
}
