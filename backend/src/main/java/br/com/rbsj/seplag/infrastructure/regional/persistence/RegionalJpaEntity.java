package br.com.rbsj.seplag.infrastructure.regional.persistence;

import br.com.rbsj.seplag.infrastructure.persistence.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "regionais")
public class RegionalJpaEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_externo")
    private Integer idExterno;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    public RegionalJpaEntity() {
    }

    public RegionalJpaEntity(
            Long id,
            Integer idExterno,
            String nome,
            Boolean ativo
    ) {
        this.id = id;
        this.idExterno = idExterno;
        this.nome = nome;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdExterno() {
        return idExterno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

}
