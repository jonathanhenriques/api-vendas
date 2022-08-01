package br.com.jonathanhenriques.projeto.domain.repository;

import br.com.jonathanhenriques.projeto.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


    public Optional<Cliente> findByNome(@Param("nome")String nome);

    Optional<Cliente> findByEmail(@Param("email") String email);


//    @Query(value = "SELECT * FROM Cliente c WHERE c.nome LIKE '%nome%'", nativeQuery = true) //SQL
    @Query(value = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome") //JPQL
    public List<Cliente> findAllByNomeContainingIgnoreCase(@Param("nome")String nome);

    public boolean existsByNome(String nome);

    @Query(" DELETE FROM Cliente c WHERE c.nome = :nome")
    @Modifying //Sinaliza que é um método de modificação e não de leitura
    public void deleteByNome(String nome);



//    @Query(" SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos WHERE c.id = :id ")
//    public Cliente findClienteFetchPedidos(@Param("id") Integer id);






/* VERSÃO COM ENTITYMANAGER PARA ESTUDOS
@Repository
public class Clientes

    @Autowired
    private EntityManager entityManager;


    @Transactional
    public Cliente salvar(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        if(!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletarPorId(Integer id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }


    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        String jpql = "Select c FROM Cliente c WHERE c.nome like :nome";
        TypedQuery<Cliente> query =  entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional
    public List<Cliente> obterTodos() {
        return entityManager
                .createQuery(" FROM Cliente ", Cliente.class)
                .getResultList();
    }


*/

    /* VERSÃO COM JDBCTEMPLATE ESTUDOS
@Repository
public class Clientes


    private static String INSERT = "INSERT INTO tb_clientes (nome) values (?)";
    private static String SELECT_ALL = "SELECT * FROM tb_clientes";
    private static String UPDATE = "UPDATE tb_clientes set nome = ? WHERE id = ?";
    private static String DELETE = "DELETE FROM tb_clientes where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

        public Cliente salvarCliente(Cliente cliente) {
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }

    public Cliente atualizar(Cliente cliente) {
        jdbcTemplate.update(UPDATE, new Object[]{
                cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void deletarCliente(Cliente cliente) {
        deletarClienteId(cliente.getId());
    }

    public void deletarClienteId(Integer id) {
        jdbcTemplate.update(DELETE, new Object[]{id});
    }


    public List<Cliente> buscarPorNome(String nome) {
        return jdbcTemplate.query(
                SELECT_ALL.concat(" where nome like ? "),
                new Object[]{"%" + nome + "%"},
                ObterClienteMapper());


    }

    public List<Cliente> obterTodos() {
        return jdbcTemplate.query(SELECT_ALL, ObterClienteMapper());
    }

    private RowMapper<Cliente> ObterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                return new Cliente(id, nome);
            }
        };
    }
    */
}
