package com.techchallenge.repository;

import com.techchallenge.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Verifica se um cliente com o CPF fornecido já existe
    boolean existsByCpf(String cpf);
}
