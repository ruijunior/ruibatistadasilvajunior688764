package br.com.rbsj.seplag.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "regionais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Regional extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Override
    public void validate() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da regional é obrigatório");
        }
    }
}
