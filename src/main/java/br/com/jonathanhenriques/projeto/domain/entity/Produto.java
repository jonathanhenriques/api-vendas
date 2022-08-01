package br.com.jonathanhenriques.projeto.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_produtos")

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 55)
    private String titulo;

    @Column(length = 250)
    private String descricao;

    @Column(name = "preco_unitario")
    private BigDecimal preco;

    public Produto(String titulo, BigDecimal preco) {
        this.titulo = titulo;
        this.preco = preco;
    }


}
