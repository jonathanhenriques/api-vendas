package br.com.jonathanhenriques.projeto.domain.repository;

import br.com.jonathanhenriques.projeto.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findAllByTituloContainingIgnoreCase(@Param("nome") String nome);

    List<Produto> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

    List<Produto> findByPreco(BigDecimal preco);

    List<Produto> findByPrecoGreaterThanEqualOrderByPreco(BigDecimal preco);

    List<Produto> findByPrecoLessThanOrderByPrecoDesc(BigDecimal preco);
}
