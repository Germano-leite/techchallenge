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
//import org.springframework.http.HttpStatus; // Importa HttpStatus para ResponseEntity.status()
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI; // Importa URI para ResponseEntity.created()
import java.util.List;
//import java.util.Optional;

// Define o controlador REST para gerenciar cartões
@RestController
@RequestMapping("/api/cartoes") // Alterado para "/api/cartoes" para consistência com outros exemplos
@Tag(name = "Cartões", description = "Endpoints para o gerenciamento de produtos de cartão.")
public class CartaoController {

    // Injeção do serviço de cartão
    @Autowired
    private CartaoService cartaoService;

    // Lista todos os cartões
    // Retorna uma lista de CartaoResponse
    @GetMapping
    @Operation(summary = "Listar todos os cartões disponíveis",
               description = "Retorna uma lista de todos os cartões de crédito e/ou débito cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de cartões retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class))) // Indica que retorna uma lista de CartaoResponse
    public ResponseEntity<List<CartaoResponse>> listarTodos() {
        
        return ResponseEntity.ok(cartaoService.listarTodos()); // Retorna 200 OK com a lista de cartões
    }

    // Busca um cartão pelo ID
    // Retorna um CartaoResponse ou 404 se não encontrado
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cartão por ID", description = "Retorna os dados de um cartão específico.")
    @ApiResponse(responseCode = "200", description = "Cartão encontrado com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cartão não encontrado.")
    public ResponseEntity<CartaoResponse> buscarPorId(@PathVariable final Long id) {

        return cartaoService.buscarPorId(id) // Retorna 200 OK com o cartão ou 404 Not Found
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cria um novo cartão
    // Recebe os dados do cartão no CartaoRequest
    @PostMapping
    @Operation(summary = "Cadastrar um novo cartão",
               description = "Cria um novo produto de cartão com nome, tipo, anuidade e bandeira.")
    @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    public ResponseEntity<CartaoResponse> criar(@RequestBody final CartaoRequest cartaoRequest) {
    
        final CartaoResponse cartaoResponse = cartaoService.salvar(cartaoRequest);
    
        return ResponseEntity.created(URI.create("/api/cartoes/" + cartaoResponse.getId())).body(cartaoResponse); // Retorna 201 Created com o cartão criado
    }

    // Atualiza um cartão existente
    // Recebe o ID do cartão e os novos dados no CartaoRequest  
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cartão", description = "Atualiza os dados de um cartão existente.")
    @ApiResponse(responseCode = "200", description = "Cartão atualizado com sucesso",
                 content = @Content(schema = @Schema(implementation = CartaoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cartão não encontrado.")
//@ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.") // Não ha validacao implementada ainda
    public ResponseEntity<CartaoResponse> atualizar(@PathVariable final Long id, @RequestBody final CartaoRequest cartaoRequest) {
        try {
            
            return ResponseEntity.ok(cartaoService.atualizar(id, cartaoRequest));
        } catch (RuntimeException e) {
            
            return ResponseEntity.notFound().build();// Retorna 404 Not Found se o cartão não existir  
        }
    }

    // Remove um cartão pelo ID
    // Verifica se o cartão existe antes de remover e se está associado a alguma contratação    
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cartão", description = "Remove um cartão do sistema permanentemente.")
    @ApiResponse(responseCode = "204", description = "Cartão removido com sucesso (Sem conteúdo).")
    @ApiResponse(responseCode = "404", description = "Cartão não encontrado.")
    public ResponseEntity<Void> deletar(@PathVariable final Long id) {
        cartaoService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content para exclusão bem-sucedida
    }
}