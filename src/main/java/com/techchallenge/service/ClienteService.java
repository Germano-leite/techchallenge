package com.techchallenge.service;

import com.techchallenge.domain.dto.ClienteRequest;
import com.techchallenge.domain.dto.ClienteResponse;
import com.techchallenge.model.Cliente;
import com.techchallenge.repository.ClienteRepository;
import com.techchallenge.repository.ContratacaoRepository;
import com.techchallenge.exception.BusinessException;
import com.techchallenge.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    //Injeção dos repositórios necessários
    //Usa @Autowired para injeção automática
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    //Lista todos os clientes
    //Retorna uma lista de ClienteResponse
    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    //Busca um cliente pelo ID
    //Retorna um Optional de ClienteResponse
    public Optional<ClienteResponse> buscarPorId(Long id) {
        return clienteRepository.findById(id).map(this::toResponse);
    }

    //Salva um novo cliente
    //Recebe os dados do cliente no ClienteRequest
    public ClienteResponse salvar(ClienteRequest clienteRequest) {
        //Testa se o CPF já existe
        if (clienteRepository.existsByCpf(clienteRequest.getCpf())) {
            throw new BusinessException("CPF já cadastrado no sistema.");
        }
        
        Cliente cliente = toEntity(clienteRequest);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toResponse(clienteSalvo);
    }

    //Atualiza os dados do cliente
    //Recebe o ID do cliente e os novos dados no ClienteRequest
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

    //Remove um cliente pelo ID
    //Verifica se o cliente existe e se possui contratações antes de remover
    public void deletar(Long id) {
        
        //Verifica se o cliente existe
        if (!clienteRepository.existsById(id)) {
            // Lança a exceção para "não encontrado"
            throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado.");
        }

        // Verifica se existem contratações associadas a este cliente
        if (contratacaoRepository.existsByClienteId(id)) {
        throw new BusinessException("Não é possível remover o cliente, pois ele possui cartões contratados.");
        }
        
        // Se não houver contratações, procede com a remoção
        clienteRepository.deleteById(id);
    }

    //Métodos auxiliares para conversão entre DTO e entidade
    private Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        String cpfLimpo = request.getCpf().replaceAll("[^0-9]", ""); // Sanitiza o CPF removendo pontos e traços
        cliente.setCpf(cpfLimpo);
        cliente.setEmail(request.getEmail());
        cliente.setDataNascimento(LocalDate.parse(request.getDataNascimento()));
        return cliente;
    }

    //Método auxiliar para conversão de entidade para DTO
    private ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNome(cliente.getNome());
        response.setCpf(cliente.getCpf());
        response.setEmail(cliente.getEmail());
        response.setDataNascimento(cliente.getDataNascimento().toString());
        //response.setDataCadastro(LocalDateTime.now().toString());
        return response;
    }
}