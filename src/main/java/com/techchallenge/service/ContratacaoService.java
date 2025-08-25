package com.techchallenge.service;

import com.techchallenge.domain.dto.ContratacaoRequest;
import com.techchallenge.domain.dto.ContratacaoResponse;
import com.techchallenge.exception.ResourceNotFoundException;
import com.techchallenge.model.*;
import com.techchallenge.repository.CartaoRepository;
import com.techchallenge.repository.ClienteRepository;
import com.techchallenge.repository.ContratacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContratacaoService {

    // Final garante que os repositórios sejam inicializados no construtor
    // Usa injeção de dependência via construtor
    // Repositórios necessários para operações de contratação
    private final ContratacaoRepository contratacaoRepository;
    private final ClienteRepository clienteRepository;
    private final CartaoRepository cartaoRepository;

    // Lista todas as contratações
    // Retorna uma lista de ContratacaoResponse
    public List<ContratacaoResponse> listarTodas() {
        return contratacaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Busca uma contratação pelo ID
    // Retorna um Optional de ContratacaoResponse
    public Optional<ContratacaoResponse> buscarPorId(Long id) {
        return contratacaoRepository.findById(id).map(this::toResponse);
    }

    // Lista todas as contratações de um cliente específico pelo ID do cliente
    // Retorna uma lista de ContratacaoResponse
    public List<ContratacaoResponse> listarPorCliente(Long clienteId) {
        
        // Verifica se o cliente com o ID fornecido realmente existe no banco de dados.
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente com ID " + clienteId + " não encontrado.");
        }
        
        // Se o cliente existir, busca todas as contratações associadas a esse cliente.
        return contratacaoRepository.findByClienteId(clienteId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    // Realiza uma nova contratação de cartão para um cliente
    // Recebe os dados da contratação no ContratacaoRequest
    // Retorna os dados da contratação criada no ContratacaoResponse
    public ContratacaoResponse contratarCartao(ContratacaoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + request.getClienteId() + " não encontrado"));

        Cartao cartao = cartaoRepository.findById(request.getCartaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Cartão com ID " + request.getCartaoId() + " não encontrado"));
                
        Contratacao novaContratacao = new Contratacao();
        novaContratacao.setCliente(cliente);
        novaContratacao.setCartao(cartao);
        novaContratacao.setStatus(StatusContratacao.ATIVO); // Status padrão definido no serviço

        Contratacao contratacaoSalva = contratacaoRepository.save(novaContratacao);
        return toResponse(contratacaoSalva);
    }

    // Atualiza o status de uma contratação existente
    // Recebe o ID da contratação e o novo status
    // Retorna os dados da contratação atualizada no ContratacaoResponse
    public ContratacaoResponse atualizarStatus(Long id, StatusContratacao novoStatus) {
        Contratacao contratacao = contratacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contratação com ID " + id + " não encontrada"));
        
        contratacao.setStatus(novoStatus);
        Contratacao contratacaoAtualizada = contratacaoRepository.save(contratacao);
        return toResponse(contratacaoAtualizada);
    }

    // Remove uma contratação pelo ID
    // Verifica se a contratação existe antes de remover
    public void deletar(Long id) {
        if (!contratacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contratação com ID " + id + " não encontrada");
        }
        contratacaoRepository.deleteById(id);
    }

    // Converte uma entidade Contratacao para ContratacaoResponse
    private ContratacaoResponse toResponse(Contratacao contratacao) {
        ContratacaoResponse response = new ContratacaoResponse();
        response.setId(contratacao.getId());
        response.setClienteId(contratacao.getCliente().getId());
        response.setClienteNome(contratacao.getCliente().getNome());
        response.setCartaoId(contratacao.getCartao().getId());
        response.setCartaoNome(contratacao.getCartao().getNome());
        response.setNumeroCartao(contratacao.getNumeroCartao()); 
        response.setDataContratacao(contratacao.getDataContratacao());
        response.setStatus(contratacao.getStatus().name());
        return response;
    }
}