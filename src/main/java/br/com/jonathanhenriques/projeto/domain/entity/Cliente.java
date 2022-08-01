package br.com.jonathanhenriques.projeto.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 11)
    private String cpf;

    @Column
    private String email;

    @OneToMany(mappedBy = "cliente" /*, fetch = FetchType.LAZY*//*, cascade = CascadeType.REMOVE*/)
//    @JsonIgnore
    private Set<Pedido> pedidos;


    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cliente(Integer id, String nome, String email) {
        this.nome = nome;
        this.email = email;
    }


}
