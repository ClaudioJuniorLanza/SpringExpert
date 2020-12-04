package io.github.claudiojuniorlanca.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.claudiojuniorlanca.domain.entity.Produto;
import io.github.claudiojuniorlanca.domain.repository.Produtos;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	@Autowired
	private Produtos produtosRepository;
	
	@GetMapping("{id}")
	public Produto getProdutoById(@PathVariable Integer id) {
		return produtosRepository.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "produto não encontrado"));
	}
	
	@GetMapping
	public List<Produto> findProduto( Produto filtro){
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example example = Example.of(filtro, matcher);
		return produtosRepository.findAll(example);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto save(@RequestBody @Valid Produto produto) {
		Produto produtoSalvo = produtosRepository.save(produto);
		return produtoSalvo;
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
		
		produtosRepository.findById(id).map( produtoExistente -> {
			produto.setId(produtoExistente.getId());
			produtosRepository.save(produto);
			return ResponseEntity.noContent().build();
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"produto não encontrado"));
		
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Integer id) {
		
		 produtosRepository.findById(id)
				.map( produtoEncontrado -> {
					produtosRepository.delete(produtoEncontrado);
					return produtoEncontrado;
				}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "produto não encontrado"));
		
	}
}
