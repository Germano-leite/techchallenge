package com.techchallenge.controller;

import com.techchallenge.model.Contratacao;
import com.techchallenge.model.StatusContratacao;
import com.techchallenge.service.ContratacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratacoes")
public class ContratacaoController {

    @Autowired
    private ContratacaoService contratacaoService;

    @GetMapping
    public List<Contratacao> listarTodas() {
        return contratacaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contratacao> buscarPorId(@PathVariable Long id) {
        return contratacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
  
    
    @GetMapping("/cliente/{clienteId}")
    public List<Contratacao> listarPorCliente(@PathVariable Long clienteId) {
        return contratacaoService.listarPorCliente(clienteId);
    }

    @PostMapping
    public ResponseEntity<Contratacao> contratarCartao(
            @RequestParam Long clienteId,
            @RequestParam Long cartaoId,
            @RequestParam(defaultValue = "ATIVO") StatusContratacao status
    ) {
        try {
            Contratacao contratacao = contratacaoService.contratarCartao(clienteId, cartaoId, status);
            return ResponseEntity.ok(contratacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Contratacao> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusContratacao status
    ) {
        try {
            Contratacao atualizada = contratacaoService.atualizarStatus(id, status);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contratacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
