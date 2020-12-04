package io.github.claudiojuniorlanca.domain.repository;

import io.github.claudiojuniorlanca.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
