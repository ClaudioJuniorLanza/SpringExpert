package io.github.claudiojuniorlanca.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(){
        super("Pedido não encontrado");
    }
}
