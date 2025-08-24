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