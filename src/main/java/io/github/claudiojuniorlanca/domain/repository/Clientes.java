package io.github.claudiojuniorlanca.domain.repository;

import io.github.claudiojuniorlanca.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome( @Param("nome") String nome);

    List<Cliente> findByNomeLike(String nome);

    boolean existsByNome(String nome);

    @Query(value = "delete from Cliente c where c.nome = :nome")
    @Modifying
    void deleteByNome(String nome);

    @Query(value = "select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos( @Param(value = "id") Integer id);

    /*
    //MEIO DE PERSISTENCIA COM ENTITY MANAGER COM JPA E SÓ JDBC
    private static String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "SELECT * FROM CLIENTE";
    private static String UPDATE = "update cliente set nome = ? where id = ?";
    private static String DELETE = "delete from cliente where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente){
        entityManager.persist(cliente);
        return cliente;

        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;

    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos(){
        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
        //return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }

    private RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                return new Cliente(id, nome);
            }
        };
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        entityManager.merge(cliente);
        return cliente;

        jdbcTemplate.update(UPDATE, new Object[]{
                cliente.getNome(), cliente.getId()
        });
        return cliente;


    }
    @Transactional
    public void deletar(Cliente cliente){
        //Esse caso é pra tratar quando o entity manager não está sincronizado com a
        // o objeto cliente (erro deteched)
        if(!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
        //deletar(cliente.getId());
    }
    @Transactional
    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
        //jdbcTemplate.update(DELETE, new Object[]{id});
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome){
        String jpql = "select c from Cliente c where c.nome like :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%"+ nome + "%");
        return query.getResultList();
        //return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ?"), new Object[]{"%"+nome+"%"}, obterClienteMapper());
    }
    */
}
