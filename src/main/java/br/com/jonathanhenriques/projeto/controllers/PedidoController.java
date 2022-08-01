package br.com.jonathanhenriques.projeto.controllers;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import br.com.jonathanhenriques.projeto.domain.entity.Pedido;
import br.com.jonathanhenriques.projeto.domain.repository.ClienteRepository;
import br.com.jonathanhenriques.projeto.domain.repository.PedidoRepository;
import br.com.jonathanhenriques.projeto.dto.PedidoDTO;
import br.com.jonathanhenriques.projeto.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoService service;



    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return pedidoRepository.findById(id)
                .map(p -> ResponseEntity.ok(pedidoRepository.findById(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<?> getPedidoByCliente(@PathVariable Cliente cliente) {
        return ResponseEntity.ok(pedidoRepository.findByCliente(cliente));
    }

    @GetMapping("/total/{total}")
    public ResponseEntity<List<Pedido>> getByTotal(@PathVariable BigDecimal total) {
        return ResponseEntity.ok(pedidoRepository.findByTotal(total));
    }

    @GetMapping("/total/maior/{total}")
    public ResponseEntity<List<Pedido>> getByTotalGreaterThan(@PathVariable BigDecimal total) {
        return ResponseEntity.ok(pedidoRepository.findByTotalGreaterThanOrderByTotal(total));
    }

    @GetMapping("/total/menor/{total}")
    public ResponseEntity<List<Pedido>> getByTotalLessThan(@PathVariable BigDecimal total) {
        return ResponseEntity.ok(pedidoRepository.findByTotalLessThanOrderByTotalAsc(total));
    }

    @GetMapping("/periodo/entre/{antes}/{depois}")
    public ResponseEntity<List<Pedido>> getByPedidoEntreData(@PathVariable LocalDate antes, @PathVariable LocalDate depois) {
        return ResponseEntity.ok(pedidoRepository.findAllByDataPedidoBetween(antes, depois));
    }

    @GetMapping("/listarPedidos")
    public ResponseEntity<List<Pedido>> getAll() {
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    @PostMapping
    public Integer postPedido(@RequestBody PedidoDTO dto){
        ResponseEntity<Pedido> pedido = service.postPedido(dto);
        return pedido.getBody().getId();
    }




    @PutMapping
    public ResponseEntity<Pedido> putPedido(@RequestBody Pedido pedido) {
        Optional<Cliente> clienteDoPedido = clienteRepository.findById(pedido.getCliente().getId());
        if (clienteDoPedido.isPresent() && pedidoRepository.findById(pedido.getId()).isPresent()) {
            return ResponseEntity.ok(pedidoRepository.save(pedido));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable Integer id) {
        return pedidoRepository.findById(id)
                .map(p -> {
                    pedidoRepository.delete(p);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }


}
