package br.com.jonathanhenriques.projeto.service;

import br.com.jonathanhenriques.projeto.domain.entity.Pedido;
import br.com.jonathanhenriques.projeto.dto.PedidoDTO;
import org.springframework.http.ResponseEntity;

public interface PedidoService {

    ResponseEntity<Pedido> postPedido(PedidoDTO dto);

}
