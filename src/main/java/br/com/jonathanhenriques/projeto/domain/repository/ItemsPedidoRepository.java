package br.com.jonathanhenriques.projeto.domain.repository;

import br.com.jonathanhenriques.projeto.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
