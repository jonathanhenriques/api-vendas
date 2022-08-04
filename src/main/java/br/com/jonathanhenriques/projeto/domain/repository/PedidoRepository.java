package br.com.jonathanhenriques.projeto.domain.repository;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import br.com.jonathanhenriques.projeto.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

     @Query(" select p from Pedido p left join fetch p.itens where p.id = :id ")
     Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);

     List<Pedido> findByCliente(Cliente cliente);

     List<Pedido> findByTotal(BigDecimal preco);

     List<Pedido> findByTotalGreaterThanOrderByTotal(BigDecimal preco);

     List<Pedido> findByTotalLessThanOrderByTotalAsc(BigDecimal preco);

     List<Pedido> findAllByDataPedidoBetween(LocalDate antesDataPedido,LocalDate depoisDataPedido);


}
