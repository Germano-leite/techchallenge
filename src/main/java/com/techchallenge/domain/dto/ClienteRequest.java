package com.techchallenge.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro e atualização de um cliente.")
public class ClienteRequest {

    @NotBlank(message = "O nome não pode estar vazio.")
    @Schema(description = "Nome completo do cliente.", example = "Ana Maria")
    private String nome;

    @NotBlank(message = "O CPF não pode estar vazio.")
    @Pattern(regexp = "^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}|\\d{1,11})$", message = "Formato de CPF inválido. O formato deve ser XXX.XXX.XXX-XX ou conter 1 a 11 dígitos numéricos.")
    @Schema(description = "Número do CPF do cliente. Deve ser único. Pode ser enviado com pontuação (11 dígitos) ou apenas com 1 a 11 dígitos numéricos. ", example = "123.456.789-01")
    private String cpf;

    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Schema(description = "Endereço de e-mail do cliente. Deve ser único.", example = "ana.maria@exemplo.com")
    private String email;

    @NotNull(message = "A data de nascimento não pode estar vazia.")
    @Schema(description = "Data de nascimento do cliente.", example = "1990-05-15")
    private String dataNascimento;
}