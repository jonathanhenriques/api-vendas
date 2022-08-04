package br.com.jonathanhenriques.projeto.rest.controllers;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import br.com.jonathanhenriques.projeto.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping(value = "/ola/{nome}")
    public String helloCliente(@PathVariable("nome") String nomeCliente) {
        return String.format("Hello %s ", nomeCliente);
    }

    @GetMapping("/listaNomes/{nome}")
    public ResponseEntity<List<Cliente>> getAllByNome(@PathVariable("nome") String nomeCliente) {
        return ResponseEntity.ok(clienteRepository.findAllByNomeContainingIgnoreCase(nomeCliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Integer id) {
        return clienteRepository.findById(id)
                .map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());

    }

    @GetMapping("/personalizada")
    public ResponseEntity<List<Cliente>> getClientePersonalizado(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example exemplo = Example.of(filtro, matcher);
        return ResponseEntity.ok(clienteRepository.findAll(exemplo));

    }

    @GetMapping("/listaClientes")
    public ResponseEntity<List<Cliente>> listaClientes() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PostMapping
    public ResponseEntity postCliente(@RequestBody Cliente cliente) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
    }

    @PutMapping
    public ResponseEntity<Cliente> putCliente(@RequestBody Cliente cliente) {
        return clienteRepository.findById(cliente.getId())
                .map(c -> ResponseEntity.ok(clienteRepository.save(cliente)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable("id") Integer idCliente) {
        if (clienteRepository.existsById(idCliente)) {
            clienteRepository.deleteById(idCliente);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
