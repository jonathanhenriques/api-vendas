package br.com.jonathanhenriques.projeto.controllers;

import br.com.jonathanhenriques.projeto.domain.entity.Produto;
import br.com.jonathanhenriques.projeto.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Integer id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(produto))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/listarProdutos") //all
    public ResponseEntity<List<Produto>> getAllProdutos() {
        return ResponseEntity.ok().body(produtoRepository.findAll());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Produto>> getAllByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(produtoRepository.findAllByTituloContainingIgnoreCase(titulo));
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Produto>> getAllByDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(produtoRepository.findAllByDescricaoContainingIgnoreCase(descricao));
    }

    @GetMapping("/preco/{preco}")
    public ResponseEntity<List<Produto>> getAllByPreco(@PathVariable BigDecimal preco) {
        return ResponseEntity.ok(produtoRepository.findByPreco(preco));
    }

    @GetMapping("/preco/menorDesc/{preco}")
    public ResponseEntity<List<Produto>> getAllLessThanPrecoDesc(@PathVariable BigDecimal preco) {
        return ResponseEntity.ok(produtoRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
    }

    @GetMapping("/preco/maior/{preco}")
    public ResponseEntity<List<Produto>> getAllGreaterThanPreco(@PathVariable BigDecimal preco) {
        return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThanEqualOrderByPreco(preco));
    }

    @GetMapping("personalizada")
    public ResponseEntity<List<Produto>> getPersonalizado(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
                Example exemplo = Example.of(filtro, matcher);
                return ResponseEntity.ok(produtoRepository.findAll(exemplo));
    }


    @PostMapping
    public ResponseEntity<Object> postProduto(@RequestBody Produto produto) {
        return produtoRepository.findById(produto.getId())
                .map(p -> ResponseEntity.badRequest().build())
                .orElse(ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)));

//        if(produtoRepository.findById(produto.getId()).isPresent())
//            return ResponseEntity.badRequest().build();
//        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    @PutMapping
    public ResponseEntity<Produto> putProduto(@RequestBody Produto produto) {
        return produtoRepository.findById(produto.getId())
                .map(p -> ResponseEntity.ok(produtoRepository.save(p)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable Integer id) {
        return produtoRepository.findById(id)
                .map(p -> {
                    produtoRepository.delete(p);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
