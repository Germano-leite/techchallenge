package com.techchallenge.service;

import com.techchallenge.model.Cartao;
import com.techchallenge.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public List<Cartao> listarTodos() {
        return cartaoRepository.findAll();
    }

    public Optional<Cartao> buscarPorId(Long id) {
        return cartaoRepository.findById(id);
    }

    public Cartao salvar(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public Cartao atualizar(Long id, Cartao novoCartao) {
        return cartaoRepository.findById(id).map(cartao -> {
            cartao.setNome(novoCartao.getNome());
            cartao.setTipo(novoCartao.getTipo());
            cartao.setAnuidade(novoCartao.getAnuidade());
            cartao.setBandeira(novoCartao.getBandeira());
            return cartaoRepository.save(cartao);
        }).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
    }

    public void deletar(Long id) {
        cartaoRepository.deleteById(id);
    }
}
