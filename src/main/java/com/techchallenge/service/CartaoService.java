package com.techchallenge.service;

import com.techchallenge.domain.dto.CartaoRequest;
import com.techchallenge.domain.dto.CartaoResponse;
import com.techchallenge.model.Bandeira;
import com.techchallenge.model.Cartao;
import com.techchallenge.model.TipoCartao;
import com.techchallenge.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public List<CartaoResponse> listarTodos() {
        return cartaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<CartaoResponse> buscarPorId(Long id) {
        return cartaoRepository.findById(id).map(this::toResponse);
    }

    public CartaoResponse salvar(CartaoRequest cartaoRequest) {
        Cartao cartao = toEntity(cartaoRequest);
        Cartao cartaoSalvo = cartaoRepository.save(cartao);
        return toResponse(cartaoSalvo);
    }

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

    public void deletar(Long id) {
        cartaoRepository.deleteById(id);
    }

    private Cartao toEntity(CartaoRequest request) {
        Cartao cartao = new Cartao();
        cartao.setNome(request.getNome());
        cartao.setTipo(TipoCartao.valueOf(request.getTipo().toUpperCase()));
        cartao.setAnuidade(request.getAnuidade());
        cartao.setBandeira(Bandeira.valueOf(request.getBandeira().toUpperCase()));
        return cartao;
    }

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