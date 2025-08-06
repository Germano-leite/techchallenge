
package com.techchallenge.service;

import com.techchallenge.model.Cliente;
import com.techchallenge.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente novoCliente) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(novoCliente.getNome());
            cliente.setCpf(novoCliente.getCpf());
            cliente.setEmail(novoCliente.getEmail());
            cliente.setDataNascimento(novoCliente.getDataNascimento());
            return clienteRepository.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
