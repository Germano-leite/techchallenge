package com.techchallenge.controller;

import com.techchallenge.domain.dto.CartaoRequest; // Importa o DTO de Requisição
import com.techchallenge.domain.dto.CartaoResponse; // Importa o DTO de Resposta
import com.techchallenge.service.CartaoService; // Mantém a injeção do seu serviço original

// Importações das anotações do Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importa HttpStatus para ResponseEntity.status()
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI; // Importa URI para ResponseEntity.created()
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes") // Alterado para "/api/cartoes" para consistência com outros exemplos
@Tag(name = "Cartões", description = "Endpoints para o gerenciamento de produtos de cartão.")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @GetMapping
    @Operation(summary = "Listar todos os cartões disponíveis",
               description = "Retorna uma lista de todos os cartões de crédito e/ou débito cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de cartões retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class))) // Indica que retorna uma lista de CartaoResponse
    public ResponseEntity<List<CartaoResponse>> listarTodos() {
        // Assume que o serviço agora retorna List<CartaoResponse>
        return ResponseEntity.ok(cartaoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cartão por ID", description = "Retorna os dados de um cartão específico.")
    @ApiResponse(responseCode = "200", description = "Cartão encontrado com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cartão não encontrado.")
    public ResponseEntity<CartaoResponse> buscarPorId(@PathVariable final Long id) {
        // Assume que o serviço agora retorna Optional<CartaoResponse>
        return cartaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo cartão",
               description = "Cria um novo produto de cartão com nome, tipo, anuidade e bandeira.")
    @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    public ResponseEntity<CartaoResponse> criar(@RequestBody final CartaoRequest cartaoRequest) {
        // Assume que o serviço agora aceita CartaoRequest e retorna CartaoResponse
        final CartaoResponse cartaoResponse = cartaoService.salvar(cartaoRequest);
        // Retorna 201 Created e a URI do novo recurso
        return ResponseEntity.created(URI.create("/api/cartoes/" + cartaoResponse.getId())).body(cartaoResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cartão", description = "Atualiza os dados de um cartão existente.")
    @ApiResponse(responseCode = "200", description = "Cartão atualizado com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cartão não encontrado.")
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    public ResponseEntity<CartaoResponse> atualizar(@PathVariable final Long id, @RequestBody final CartaoRequest cartaoRequest) {
        try {
            // Assume que o serviço agora aceita CartaoRequest e retorna CartaoResponse
            return ResponseEntity.ok(cartaoService.atualizar(id, cartaoRequest));
        } catch (RuntimeException e) {
            // Um tratamento de erro mais específico pode ser necessário aqui.
            // Por enquanto, o 404 é capturado para "não encontrado".
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cartão", description = "Remove um cartão do sistema permanentemente.")
    @ApiResponse(responseCode = "204", description = "Cartão removido com sucesso (Sem conteúdo).")
    @ApiResponse(responseCode = "404", description = "Cartão não encontrado.")
    public ResponseEntity<Void> deletar(@PathVariable final Long id) {
        cartaoService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content para exclusão bem-sucedida
    }
}