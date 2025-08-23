package com.techchallenge.service;

import com.techchallenge.domain.dto.ClienteRequest;
import com.techchallenge.domain.dto.ClienteResponse;
import com.techchallenge.model.Cliente;
import com.techchallenge.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ClienteResponse> buscarPorId(Long id) {
        return clienteRepository.findById(id).map(this::toResponse);
    }

    public ClienteResponse salvar(ClienteRequest clienteRequest) {
        Cliente cliente = toEntity(clienteRequest);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toResponse(clienteSalvo);
    }

    public ClienteResponse atualizar(Long id, ClienteRequest novoClienteRequest) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        clienteExistente.setNome(novoClienteRequest.getNome());
        clienteExistente.setCpf(novoClienteRequest.getCpf());
        clienteExistente.setEmail(novoClienteRequest.getEmail());
        clienteExistente.setDataNascimento(LocalDate.parse(novoClienteRequest.getDataNascimento()));

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return toResponse(clienteAtualizado);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());
        cliente.setDataNascimento(LocalDate.parse(request.getDataNascimento()));
        return cliente;
    }

    private ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNome(cliente.getNome());
        response.setCpf(cliente.getCpf());
        response.setEmail(cliente.getEmail());
        response.setDataNascimento(cliente.getDataNascimento().toString());
        // Ajuste no campo de data de cadastro
        // É comum que a entidade Cliente já tenha um campo 'dataCadastro'
        // Se a sua entidade não tiver, você pode definir como LocalDateTime.now()
        // ou remover o campo do DTO se ele não for relevante para a resposta
        response.setDataCadastro(LocalDateTime.now().toString());
        return response;
    }
}