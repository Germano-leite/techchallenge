package com.techchallenge.controller;

import com.techchallenge.domain.dto.ClienteRequest;
import com.techchallenge.domain.dto.ClienteResponse;
import com.techchallenge.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URI;
import java.util.List;
//import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para o gerenciamento de clientes.")
public class ClienteController {
    
    //Injeção do serviço de cliente
    @Autowired
    private ClienteService clienteService;

    //Lista todos os clientes
    //Retorna uma lista de ClienteResponse  
    @GetMapping
    @Operation(summary = "Listar todos os clientes",
               description = "Retorna uma lista completa de todos os clientes cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    public List<ClienteResponse> listarTodos() {
        return clienteService.listarTodos();
    }

    //Busca um cliente pelo ID
    //Retorna um ClienteResponse ou 404 se não encontrado   
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico.")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso",
                 content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Cria um novo cliente
    //Recebe os dados do cliente no ClienteRequest   
    @PostMapping
    @Operation(summary = "Cadastrar um novo cliente",
               description = "Cria um novo cliente com nome, CPF, e-mail e data de nascimento.")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
                 content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "400", description = "CPF já cadastrado do sistema.")
    public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest clienteRequest) {
        ClienteResponse clienteResponse = clienteService.salvar(clienteRequest);
        return ResponseEntity.created(URI.create("/api/clientes/" + clienteResponse.getId())).body(clienteResponse);
    }

    //Atualiza um cliente existente
    //Recebe o ID do cliente e os novos dados no ClienteRequest
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente.")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                 content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest) {
        try {
            return ResponseEntity.ok(clienteService.atualizar(id, clienteRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Remove um cliente pelo ID
    //Verifica se o cliente existe antes de remover e se possui contratações
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente", description = "Remove um cliente do sistema permanentemente.")
    @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso (Sem conteúdo).")
    @ApiResponse(responseCode = "400", description = "Cliente não pode ser removido pois possui contratação.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}