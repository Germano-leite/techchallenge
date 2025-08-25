package com.techchallenge.repository;

import com.techchallenge.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;//importa o JPA repository

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}

