package br.com.jonathanhenriques.projeto.service.implementacao;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import br.com.jonathanhenriques.projeto.domain.entity.ItemPedido;
import br.com.jonathanhenriques.projeto.domain.entity.Pedido;
import br.com.jonathanhenriques.projeto.domain.entity.Produto;
import br.com.jonathanhenriques.projeto.domain.repository.ClienteRepository;
import br.com.jonathanhenriques.projeto.domain.repository.ItemsPedidoRepository;
import br.com.jonathanhenriques.projeto.domain.repository.PedidoRepository;
import br.com.jonathanhenriques.projeto.domain.repository.ProdutoRepository;
import br.com.jonathanhenriques.projeto.exception.RegraNegocioException;
import br.com.jonathanhenriques.projeto.rest.dto.ItemPedidoDTO;
import br.com.jonathanhenriques.projeto.rest.dto.PedidoDTO;
import br.com.jonathanhenriques.projeto.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public Pedido postPedido(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
//        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itensPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }


    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }



}
