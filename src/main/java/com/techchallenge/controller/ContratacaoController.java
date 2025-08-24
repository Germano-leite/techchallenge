package com.techchallenge.controller;

import com.techchallenge.domain.dto.ContratacaoRequest;
import com.techchallenge.domain.dto.ContratacaoResponse;
import com.techchallenge.model.StatusContratacao; // Verifique o import
import com.techchallenge.service.ContratacaoService; // Importe o Service
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contratacoes")
@Tag(name = "Contratações", description = "Endpoints para o gerenciamento de contratações de cartões.")
public class ContratacaoController {

    @Autowired
    private ContratacaoService contratacaoService; // Injete o Service, não o Repository

    @GetMapping
    @Operation(summary = "Listar todas as contratações")
    public ResponseEntity<List<ContratacaoResponse>> listarTodas() {
        return ResponseEntity.ok(contratacaoService.listarTodas());
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contratações por cliente")
    public ResponseEntity<List<ContratacaoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contratacaoService.listarPorCliente(clienteId));
    }

    @PostMapping
    @Operation(summary = "Realizar uma nova contratação")
    public ResponseEntity<ContratacaoResponse> contratar(@RequestBody ContratacaoRequest contratacaoRequest) {
        ContratacaoResponse response = contratacaoService.contratarCartao(contratacaoRequest);
        return ResponseEntity.created(URI.create("/api/contratacoes/" + response.getId())).body(response);
    }
    
    @PatchMapping("/{id}/status") // Usar PATCH para atualizações parciais é mais semântico
    @Operation(summary = "Atualizar status de uma contratação")
    public ResponseEntity<ContratacaoResponse> atualizarStatus(@PathVariable Long id, @RequestParam("status") StatusContratacao novoStatus) {
        return ResponseEntity.ok(contratacaoService.atualizarStatus(id, novoStatus));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma contratação")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contratacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
/*package com.techchallenge.controller;

import com.techchallenge.domain.dto.ContratacaoRequest;
import com.techchallenge.domain.dto.ContratacaoResponse;
import com.techchallenge.model.Cartao;
import com.techchallenge.model.Cliente;
import com.techchallenge.model.Contratacao;
import com.techchallenge.model.StatusContratacao;
import com.techchallenge.repository.CartaoRepository;
import com.techchallenge.repository.ClienteRepository;
import com.techchallenge.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.techchallenge.service.ContratacaoService; // Importe o Service
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;



@RestController 
@RequestMapping("/api/contratacoes")
@Tag(name = "Contratações", description = "Endpoints para o gerenciamento de contratações de cartões.")
public class ContratacaoController {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public List<ContratacaoResponse> listarTodas() {
        return contratacaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ContratacaoResponse> buscarPorId(Long id) {
        return contratacaoRepository.findById(id).map(this::toResponse);
    }

    public List<ContratacaoResponse> listarPorCliente(Long clienteId) {
        return contratacaoRepository.findAll().stream()
                .filter(c -> c.getCliente().getId().equals(clienteId))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ContratacaoResponse contratarCartao(ContratacaoRequest contratacaoRequest) {
        Cliente cliente = clienteRepository.findById(contratacaoRequest.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Cartao cartao = cartaoRepository.findById(contratacaoRequest.getCartaoId())
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        Contratacao contratacao = new Contratacao();
        contratacao.setCliente(cliente);
        contratacao.setCartao(cartao);
        contratacao.setDataContratacao(LocalDate.now());
        contratacao.setStatus(StatusContratacao.ATIVO); // Padrão 'ATIVO'

        Contratacao contratacaoSalva = contratacaoRepository.save(contratacao);
        return toResponse(contratacaoSalva);
    }

    public ContratacaoResponse atualizarStatus(Long id, StatusContratacao novoStatus) {
        return contratacaoRepository.findById(id).map(c -> {
            c.setStatus(novoStatus);
            Contratacao contratacaoAtualizada = contratacaoRepository.save(c);
            return toResponse(contratacaoAtualizada);
        }).orElseThrow(() -> new RuntimeException("Contratação não encontrada"));
    }

    public void deletar(Long id) {
        contratacaoRepository.deleteById(id);
    }

    // Método de conversão de Entidade para DTO de Resposta
    private ContratacaoResponse toResponse(Contratacao contratacao) {
        ContratacaoResponse response = new ContratacaoResponse();
        response.setId(contratacao.getId());
        response.setClienteId(contratacao.getCliente().getId());
        response.setCartaoId(contratacao.getCartao().getId());
        // Correção: Converte LocalDate para LocalDateTime (se o DTO espera LocalDateTime)
        // ou ajuste o DTO para LocalDate se não precisar da hora
        response.setDataContratacao(contratacao.getDataContratacao().atStartOfDay());
        response.setStatus(contratacao.getStatus().name());
        return response;
    }
}
*/