package br.com.jonathanhenriques.projeto.service.implementacao;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import br.com.jonathanhenriques.projeto.domain.entity.ItemPedido;
import br.com.jonathanhenriques.projeto.domain.entity.Pedido;
import br.com.jonathanhenriques.projeto.domain.entity.Produto;
import br.com.jonathanhenriques.projeto.domain.repository.ClienteRepository;
import br.com.jonathanhenriques.projeto.domain.repository.ItemsPedidoRepository;
import br.com.jonathanhenriques.projeto.domain.repository.PedidoRepository;
import br.com.jonathanhenriques.projeto.domain.repository.ProdutoRepository;
import br.com.jonathanhenriques.projeto.dto.ItemPedidoDTO;
import br.com.jonathanhenriques.projeto.dto.PedidoDTO;
import br.com.jonathanhenriques.projeto.exception.RegraNegocioException;
import br.com.jonathanhenriques.projeto.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImplementacao implements PedidoService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemsPedidoRepository itensPedidoRepository;


    @Override
    @Transactional
    public ResponseEntity<Pedido> postPedido(@RequestBody PedidoDTO dto) {
        Pedido pedido = new Pedido();

        if (clienteRepository.findById(dto.getCliente()).isPresent()) {
            Cliente cliente = clienteRepository
                    .findById(dto.getCliente())
                    .orElseThrow(
                            () -> new RegraNegocioException("código de cliente inválido"));
            LocalDate data = LocalDate.now();
            BigDecimal total = dto.getTotal();
            List<ItemPedido> itemsPedidos = converterListaItens(pedido, dto.getItens());
            pedidoRepository.save(pedido);
            itensPedidoRepository.saveAll(itemsPedidos);

            pedido.setCliente(cliente);
            pedido.setDataPedido(data);
            pedido.setTotal(total);
            pedido.setItens(itemsPedidos);

            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        }
        return ResponseEntity.badRequest().build();
    }


    private List<ItemPedido> converterListaItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Não existem itens para realizar o pedido!");
        }

        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtoRepository
                    .findById(idProduto)
                    .orElseThrow(
                            () -> new RegraNegocioException("código de cliente inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;

        }).collect(Collectors.toList());
    }

}
