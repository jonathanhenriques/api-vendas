package br.com.jonathanhenriques.projeto.domain.repository;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import br.com.jonathanhenriques.projeto.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {


     List<Pedido> findByCliente(Cliente cliente);

     List<Pedido> findByTotal(BigDecimal preco);

     List<Pedido> findByTotalGreaterThanOrderByTotal(BigDecimal preco);

     List<Pedido> findByTotalLessThanOrderByTotalAsc(BigDecimal preco);

     List<Pedido> findAllByDataPedidoBetween(LocalDate antesDataPedido,LocalDate depoisDataPedido);


}
