package br.com.jonathanhenriques.projeto.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties(value = "pedidos")
    private Cliente cliente;

    @Column(name = "data_pedido")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate dataPedido;

    @Column(length = 20, precision = 20, scale = 2) //10000.00
    private BigDecimal total;

    @OneToMany(mappedBy = "pedido")
    @JsonIgnoreProperties(value = "pedido")
    private List<ItemPedido> itens;

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

}
