package com.techchallenge.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para a contratação de um cartão por um cliente.")
public class ContratacaoRequest {

    @NotNull(message = "O ID do cliente não pode estar vazio.")
    @Schema(description = "ID do cliente que está contratando o cartão.", example = "1")
    private Long clienteId;

    @NotNull(message = "O ID do cartão não pode estar vazio.")
    @Schema(description = "ID do cartão que está sendo contratado.", example = "1")
    private Long cartaoId;
}