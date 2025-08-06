package com.techchallenge.service;
 
import com.techchallenge.model.*;
import com.techchallenge.repository.CartaoRepository;
import com.techchallenge.repository.ClienteRepository;
import com.techchallenge.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratacaoService {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public List<Contratacao> listarTodas() {
        return contratacaoRepository.findAll();
    }

    public Optional<Contratacao> buscarPorId(Long id) {
        return contratacaoRepository.findById(id);
    }
 
    public List<Contratacao> listarPorCliente(Long clienteId) {
        return contratacaoRepository.findAll().stream()
                .filter(c -> c.getCliente().getId().equals(clienteId))
                .toList();
    }

    public Contratacao contratarCartao(Long clienteId, Long cartaoId, StatusContratacao status) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        Contratacao contratacao = new Contratacao();
        contratacao.setCliente(cliente);
        contratacao.setCartao(cartao);
        contratacao.setDataContratacao(LocalDate.now());
        contratacao.setStatus(status);

        return contratacaoRepository.save(contratacao);
    }

    public Contratacao atualizarStatus(Long id, StatusContratacao novoStatus) {
        return contratacaoRepository.findById(id).map(c -> {
            c.setStatus(novoStatus);
            return contratacaoRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Contratação não encontrada"));
    }

    public void deletar(Long id) {
        contratacaoRepository.deleteById(id);
    }
}
