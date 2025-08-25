package com.techchallenge.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro e atualização de um cliente.")
public class ClienteRequest {

    @NotBlank(message = "O nome não pode estar vazio.")
    @Schema(description = "Nome completo do cliente.", example = "Ana Maria")
    private String nome;

    @NotBlank(message = "O CPF não pode estar vazio.")
    @Schema(description = "Número do CPF do cliente. Deve ser único.", example = "123.456.789-01")
    private String cpf;

    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Schema(description = "Endereço de e-mail do cliente. Deve ser único.", example = "ana.maria@exemplo.com")
    private String email;

    @NotNull(message = "A data de nascimento não pode estar vazia.")
    @Schema(description = "Data de nascimento do cliente.", example = "1990-05-15")
    private String dataNascimento;
}