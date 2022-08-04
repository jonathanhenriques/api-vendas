package br.com.jonathanhenriques.projeto.service;

import br.com.jonathanhenriques.projeto.domain.entity.Pedido;
import br.com.jonathanhenriques.projeto.rest.dto.PedidoDTO;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PedidoService {

    Pedido postPedido(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

//    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
