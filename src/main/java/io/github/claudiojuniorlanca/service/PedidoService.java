package io.github.claudiojuniorlanca.service;

import io.github.claudiojuniorlanca.domain.entity.Pedido;
import io.github.claudiojuniorlanca.domain.enums.StatusPedido;
import io.github.claudiojuniorlanca.rest.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaPedido(Integer id, StatusPedido status);
}
