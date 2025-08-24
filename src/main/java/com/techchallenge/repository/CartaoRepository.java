package com.techchallenge.repository;

import com.techchallenge.model.Cartao;
//importa o JPA repository
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}

