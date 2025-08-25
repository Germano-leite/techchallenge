package com.techchallenge.controller;

import com.techchallenge.domain.dto.ContratacaoRequest;
import com.techchallenge.domain.dto.ContratacaoResponse;
import com.techchallenge.model.StatusContratacao;
import com.techchallenge.service.ContratacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contratacoes")
@Tag(name = "Contratações", description = "Endpoints para o gerenciamento de contratações.")
@AllArgsConstructor
public class ContratacaoController {

    // Injeção do serviço de contratação   
    private final ContratacaoService contratacaoService;

    // Lista todas as contratações
    // Retorna uma lista de ContratacaoResponse 
    @GetMapping
    @Operation(summary = "Listar todas as contratações")
    @ApiResponse(responseCode = "200", description = "Lista de contratações retornada com sucesso")
    public ResponseEntity<List<ContratacaoResponse>> listarTodas() {
        // Retorna 200 OK com a lista no corpo da resposta
        return ResponseEntity.ok(contratacaoService.listarTodas());
    }

    // Busca uma contratação pelo ID
    // Retorna um ContratacaoResponse ou 404 se não encontrado  
    @GetMapping("/{id}")
    @Operation(summary = "Buscar contratação por ID")
    @ApiResponse(responseCode = "200", description = "Contratação encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Contratação não encontrada")
    public ResponseEntity<ContratacaoResponse> buscarPorId(@PathVariable Long id) {
        // O service retorna um Optional<ContratacaoResponse>
        return contratacaoService.buscarPorId(id)
                .map(contratacao -> ResponseEntity.ok(contratacao)) // Se encontrar, retorna 200 OK com o objeto
                .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    // Lista todas as contratações de um cliente específico pelo ID do cliente
    // Retorna uma lista de ContratacaoResponse 
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contratações por cliente")
    @ApiResponse(responseCode = "200", description = "Lista de contratações do cliente retornada com sucesso")
    @ApiResponse(responseCode = "204", description = "O cliente existe, mas não possui contratações")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ResponseEntity<List<ContratacaoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        
        List<ContratacaoResponse> contratacoes = contratacaoService.listarPorCliente(clienteId);

        // Verifica se a lista está vazia
        if (contratacoes.isEmpty()) {
            // Se estiver vazia, retorne 204 No Content
            return ResponseEntity.noContent().build();
        }

        // Se houver contratações, retorne 200 OK com a lista
        return ResponseEntity.ok(contratacoes);
    }
    
    // Realiza uma nova contratação de cartão para um cliente
    // Recebe os dados da contratação no ContratacaoRequest
    @PostMapping
    @Operation(summary = "Realizar uma nova contratação")
    @ApiResponse(responseCode = "201", description = "Contratação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
    public ResponseEntity<ContratacaoResponse> contratar(@Valid @RequestBody ContratacaoRequest request) {
        ContratacaoResponse response = contratacaoService.contratarCartao(request);
        // Retorna 201 Created, com a URL do novo recurso no cabeçalho e o objeto criado no corpo
        return ResponseEntity.created(URI.create("/api/contratacoes/" + response.getId())).body(response);
    }
    
    // Atualiza o status de uma contratação existente
    // Recebe o ID da contratação e o novo status como parâmetro
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status de uma contratação")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Contratação não encontrada")
    public ResponseEntity<ContratacaoResponse> atualizarStatus(@PathVariable Long id, @RequestParam("status") StatusContratacao novoStatus) {
        // O try-catch aqui é uma forma simples de lidar com a exceção
        try {
            return ResponseEntity.ok(contratacaoService.atualizarStatus(id, novoStatus));
        } catch (RuntimeException e) { // Idealmente, uma exceção mais específica
            return ResponseEntity.notFound().build();
        }
    }

    // Remove uma contratação pelo ID
    // Verifica se a contratação existe antes de remover
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma contratação")
    @ApiResponse(responseCode = "204", description = "Contratação removida com sucesso")
    @ApiResponse(responseCode = "404", description = "Contratação não encontrada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contratacaoService.deletar(id);
        // Retorna 204 No Content, que é o padrão para exclusões bem-sucedidas
        return ResponseEntity.noContent().build();
    }
}