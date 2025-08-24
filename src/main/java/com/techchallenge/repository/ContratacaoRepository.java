package com.techchallenge.repository;

import com.techchallenge.model.Contratacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratacaoRepository extends JpaRepository<Contratacao, Long> {
    // Busca todas as contratações de um cliente específico pelo ID do cliente
    List<Contratacao> findByClienteId(Long clienteId);

    // Verifica se existem contratações associadas a um cliente específico
    boolean existsByClienteId(Long clienteId);
}

