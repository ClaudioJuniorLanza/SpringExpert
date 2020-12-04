package io.github.claudiojuniorlanca.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.claudiojuniorlanca.domain.entity.Cliente;
import io.github.claudiojuniorlanca.domain.repository.Clientes;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private Clientes clientesRepository;

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
    	
    	return clientesRepository.findById(id)
    			.orElseThrow( () -> 
    				new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente não encontado")
    			);
    	
    	/*
        Optional<Cliente> cliente = clientesRepository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
        */
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save( @RequestBody @Valid Cliente cliente){
        Cliente clienteSalvo = clientesRepository.save(cliente);
        return clienteSalvo;

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id){
    	
    	 clientesRepository.findById(id)
    		.map(clienteAchado -> {
    			clientesRepository.delete(clienteAchado);
    			return clienteAchado;
    		})
    		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente não encontado"));
    		
    	

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid Cliente cliente){

         clientesRepository.findById(id)
                .map( clienteexistente ->  {
                    cliente.setId(clienteexistente.getId());
                    clientesRepository.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente não encontado"));
 		
    }
    
    @GetMapping
    public List<Cliente> find( Cliente filtro) {
    	ExampleMatcher matcher = ExampleMatcher
    								.matching()
    								.withIgnoreCase()
    								.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    	
    	Example example = Example.of(filtro, matcher);
    	return clientesRepository.findAll(example);
    	
    								
    }
}
