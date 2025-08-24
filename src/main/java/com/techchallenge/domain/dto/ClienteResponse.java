package com.techchallenge.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
//import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Dados de um cliente retornados pela API.")
public class ClienteResponse {

    @Schema(description = "ID único gerado para o cliente.")
    private Long id;

    @Schema(description = "Nome completo do cliente.")
    private String nome;

    @Schema(description = "Número do CPF do cliente.")
    private String cpf;

    @Schema(description = "Endereço de e-mail do cliente.")
    private String email;

    @Schema(description = "Data de nascimento do cliente.")
    private String dataNascimento;

    //@Schema(description = "Data e hora do cadastro do cliente.", example = "2025-08-11T10:00:00Z")
    //private String dataCadastro;
} 
