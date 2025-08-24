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
    private final ContratacaoRepository contratacaoRepository;
    private final ClienteRepository clienteRepository;
    private final CartaoRepository cartaoRepository;

    public List<ContratacaoResponse> listarTodas() {
        return contratacaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ContratacaoResponse> buscarPorId(Long id) {
        return contratacaoRepository.findById(id).map(this::toResponse);
    }

    public List<ContratacaoResponse> listarPorCliente(Long clienteId) {
        return contratacaoRepository.findByClienteId(clienteId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
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

    public ContratacaoResponse atualizarStatus(Long id, StatusContratacao novoStatus) {
        Contratacao contratacao = contratacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contratação com ID " + id + " não encontrada"));
        
        contratacao.setStatus(novoStatus);
        Contratacao contratacaoAtualizada = contratacaoRepository.save(contratacao);
        return toResponse(contratacaoAtualizada);
    }

    public void deletar(Long id) {
        if (!contratacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contratação com ID " + id + " não encontrada");
        }
        contratacaoRepository.deleteById(id);
    }

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