package io.github.claudiojuniorlanca.rest.controller;

import io.github.claudiojuniorlanca.domain.entity.ItemPedido;
import io.github.claudiojuniorlanca.domain.entity.Pedido;
import io.github.claudiojuniorlanca.domain.enums.StatusPedido;
import io.github.claudiojuniorlanca.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.claudiojuniorlanca.rest.dto.InformacaoItemPedidoDTO;
import io.github.claudiojuniorlanca.rest.dto.InformacoesPedidoDTO;
import io.github.claudiojuniorlanca.rest.dto.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import io.github.claudiojuniorlanca.service.PedidoService;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save(@RequestBody @Valid PedidoDTO pedidoDTO){
		Pedido pedido = pedidoService.salvar(pedidoDTO);
		return pedido.getId();
	}

	@GetMapping("{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id){
		return pedidoService.obterPedidoCompleto(id)
				.map( p -> converter(p) )
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "pedido não encontrado"));
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
		String novoStatus = dto.getNovoStatus();
		pedidoService.atualizaPedido(id, StatusPedido.valueOf(novoStatus));
	}

	private InformacoesPedidoDTO converter(Pedido pedido){
		return InformacoesPedidoDTO.builder()
				.codigo(pedido.getId())
				.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.cpf(pedido.getCliente().getCpf())
				.nomeCliente(pedido.getCliente().getNome())
				.total(pedido.getTotal())
				.status(pedido.getStatus().name())
				.itens(converter(pedido.getItens()))
				.build();
	}

	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itemPedido){
		if(CollectionUtils.isEmpty(itemPedido)){
			return Collections.emptyList();
		}

		return itemPedido.stream().map( item ->
			InformacaoItemPedidoDTO.builder()
					.descricaoProduto(item.getProduto().getDescricao())
					.precoUnitario(item.getProduto().getPreco())
					.quantidade(item.getQuantidade())
					.build()
		).collect(Collectors.toList());
	}
}
