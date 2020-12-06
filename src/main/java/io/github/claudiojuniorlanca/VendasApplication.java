package io.github.claudiojuniorlanca;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.github.claudiojuniorlanca.domain.entity.Cliente;
import io.github.claudiojuniorlanca.domain.entity.Pedido;
import io.github.claudiojuniorlanca.domain.repository.Clientes;
import io.github.claudiojuniorlanca.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
    /*
    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes, @Autowired Pedidos pedidos){
        return  args -> {
            System.out.println("criando clientes");
            Cliente cliente = new Cliente(null, "Claudio");
            clientes.save(cliente);

        };
    }


    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes, @Autowired Pedidos pedidos){
        return  args -> {
            System.out.println("criando clientes");
            Cliente cliente = new Cliente("Claudio");
            clientes.save(cliente);

            Pedido p = new Pedido();
            p.setCliente(cliente);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));

            pedidos.save(p);

            Cliente fulano = clientes.findClienteFetchPedidos(cliente.getId());
            System.out.println(fulano);
            System.out.println(fulano.getPedidos());

            List<Pedido> listPedido = pedidos.findByCliente(cliente);
            listPedido.forEach(System.out::println);
            /*
            //clientes.salvar(new Cliente("Claudio"));
            //clientes.salvar(new Cliente("Junior"));

            boolean existe = clientes.existsByNome("Claudio");
            System.out.println("existe um nome Claudio ?" + existe);


            List<Cliente> todosClientes = clientes.findAll();
            //List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("atualizando clientes");
            todosClientes.forEach(c ->{
                c.setNome(c.getNome() + " atualizado");
                clientes.save(c);
                //clientes.atualizar(c);
            });

            todosClientes = clientes.findAll();
            //todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("buscando clientes");
            clientes.encontrarPorNome("Junior").forEach(System.out::println);


            System.out.println("deletando clientes");
            clientes.findAll().forEach(c->{
                clientes.delete(c);
                //clientes.deletar(c);
            });

            todosClientes = clientes.findAll();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado");
            }else{
                todosClientes.forEach(System.out::println);
            }

        };
    }
    */

}
