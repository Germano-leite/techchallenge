package com.techchallenge.service;

import com.techchallenge.domain.dto.CartaoRequest;
import com.techchallenge.domain.dto.CartaoResponse;
import com.techchallenge.exception.BusinessException;
import com.techchallenge.exception.ResourceNotFoundException;
import com.techchallenge.model.Bandeira;
import com.techchallenge.model.Cartao;
import com.techchallenge.model.TipoCartao;
import com.techchallenge.repository.CartaoRepository;
import com.techchallenge.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartaoService {

    // Injeção do repositório necessário
    // Usa @Autowired para injeção automática
    @Autowired
    private CartaoRepository cartaoRepository;

    // Injeção do repositório de contratações para validações
    @Autowired
    private ContratacaoRepository contratacaoRepository;

    // Lista todos os cartões
    // Retorna uma lista de CartaoResponse
    public List<CartaoResponse> listarTodos() {
        return cartaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Busca um cartão pelo ID
    // Retorna um Optional de CartaoResponse
    public Optional<CartaoResponse> buscarPorId(Long id) {
        return cartaoRepository.findById(id).map(this::toResponse);
    }

    // Salva um novo cartão
    // Recebe os dados do cartão no CartaoRequest
    public CartaoResponse salvar(CartaoRequest cartaoRequest) {
        Cartao cartao = toEntity(cartaoRequest);
        Cartao cartaoSalvo = cartaoRepository.save(cartao);
        return toResponse(cartaoSalvo);
    }

    // Atualiza os dados de um cartão existente
    // Recebe o ID do cartão e os novos dados no CartaoRequest
    public CartaoResponse atualizar(Long id, CartaoRequest novoCartaoRequest) {
        Cartao cartaoExistente = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        cartaoExistente.setNome(novoCartaoRequest.getNome());
        cartaoExistente.setTipo(TipoCartao.valueOf(novoCartaoRequest.getTipo().toUpperCase()));
        cartaoExistente.setAnuidade(novoCartaoRequest.getAnuidade());
        cartaoExistente.setBandeira(Bandeira.valueOf(novoCartaoRequest.getBandeira().toUpperCase()));

        Cartao cartaoAtualizado = cartaoRepository.save(cartaoExistente);
        return toResponse(cartaoAtualizado);
    }

    // Remove um cartão pelo ID
    // Verifica se o cartão existe antes de remover
    public void deletar(Long id) {
        //Verifica se o cartão existe
        if (!cartaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cartão com ID " + id + " não encontrado.");
        }

        //Verifica se este tipo de cartão foi contratado por algum cliente
        if (contratacaoRepository.existsByCartaoId(id)) {
            throw new BusinessException("Não é possível remover o cartão, pois existem clientes com este produto contratado.");
        }
        cartaoRepository.deleteById(id);
    }

    // Converte um CartaoRequest para a entidade Cartao
    private Cartao toEntity(CartaoRequest request) {
        Cartao cartao = new Cartao();
        cartao.setNome(request.getNome());
        cartao.setTipo(TipoCartao.valueOf(request.getTipo().toUpperCase()));
        cartao.setAnuidade(request.getAnuidade());
        cartao.setBandeira(Bandeira.valueOf(request.getBandeira().toUpperCase()));
        return cartao;
    }

    // Converte uma entidade Cartao para CartaoResponse
    private CartaoResponse toResponse(Cartao cartao) {
        CartaoResponse response = new CartaoResponse();
        response.setId(cartao.getId());
        response.setNome(cartao.getNome());
        response.setTipo(cartao.getTipo().name());
        response.setAnuidade(cartao.getAnuidade());
        response.setBandeira(cartao.getBandeira().name());
        return response;
    }
}