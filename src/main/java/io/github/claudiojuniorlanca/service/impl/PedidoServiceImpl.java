package io.github.claudiojuniorlanca.service.impl;

import io.github.claudiojuniorlanca.domain.entity.Cliente;
import io.github.claudiojuniorlanca.domain.entity.ItemPedido;
import io.github.claudiojuniorlanca.domain.entity.Pedido;
import io.github.claudiojuniorlanca.domain.entity.Produto;
import io.github.claudiojuniorlanca.domain.enums.StatusPedido;
import io.github.claudiojuniorlanca.domain.repository.Clientes;
import io.github.claudiojuniorlanca.domain.repository.ItemsPedido;
import io.github.claudiojuniorlanca.domain.repository.Produtos;
import io.github.claudiojuniorlanca.exception.PedidoNaoEncontradoException;
import io.github.claudiojuniorlanca.exception.RegraNegocioException;
import io.github.claudiojuniorlanca.rest.dto.ItemPedidoDTO;
import io.github.claudiojuniorlanca.rest.dto.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.claudiojuniorlanca.domain.repository.Pedidos;
import io.github.claudiojuniorlanca.service.PedidoService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private Pedidos pedidoRepository;

	@Autowired
	private Clientes clientesRepository;

	@Autowired
	private Produtos produtosRepository;

	@Autowired
	private ItemsPedido itemsPedidoRepository;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO pedidoDTO) {
		Integer idCliente = pedidoDTO.getCliente();

		Cliente cliente = clientesRepository.findById(idCliente)
							.orElseThrow( () -> new RegraNegocioException("código de cliente não encontrado. Código: " + idCliente));

		Pedido pedido = new Pedido();
		pedido.setTotal(pedidoDTO.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setStatus(StatusPedido.REALIZADO);
		pedido.setCliente(cliente);

		List<ItemPedido> itemPedido = converterItens(pedido, pedidoDTO.getItemPedidoDTO());
		pedidoRepository.save(pedido);
		itemsPedidoRepository.saveAll(itemPedido);
		pedido.setItens(itemPedido);
		return pedido;
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return pedidoRepository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizaPedido(Integer id, StatusPedido status) {
		pedidoRepository.findById(id)
				.map( pedido -> {
					pedido.setStatus(status);
					return pedidoRepository.save(pedido);
				}).orElseThrow( () -> new PedidoNaoEncontradoException());
	}


	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
		if(itens.isEmpty()){
			throw new RegraNegocioException("Não é possível realizar um pedido sem item");
		}

		return itens.stream()
				.map( dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtosRepository.findById(idProduto)
							.orElseThrow( () -> new RegraNegocioException("código de produto inválido"));

					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setPedido(pedido);
					itemPedido.setQuantidade(dto.getQuantidade());
					itemPedido.setProduto(produto);
					return itemPedido;
				}).collect(Collectors.toList());
	}
}
