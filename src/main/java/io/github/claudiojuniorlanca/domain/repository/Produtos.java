package io.github.claudiojuniorlanca.domain.repository;

import io.github.claudiojuniorlanca.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
