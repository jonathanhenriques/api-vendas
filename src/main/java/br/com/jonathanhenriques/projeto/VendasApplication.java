package br.com.jonathanhenriques.projeto;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import br.com.jonathanhenriques.projeto.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner commandLineRunner(@Autowired ClienteRepository clienteRepository) {
        return args -> {
            Cliente c = new Cliente(null, "jonathan");
            clienteRepository.save(c);
        };

    }


    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args);
    }
}
