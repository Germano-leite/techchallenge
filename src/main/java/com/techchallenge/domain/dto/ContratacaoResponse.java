package com.techchallenge.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Dados de uma contratação retornados pela API.")
public class ContratacaoResponse {

    @Schema(description = "ID único da contratação.")
    private Long id;

    @Schema(description = "Data e hora em que a contratação foi realizada.")
    private LocalDateTime dataContratacao;

    @Schema(description = "Status atual da contratação (ativo, cancelado, etc.).", example = "Ativo")
    private String status;

    @Schema(description = "ID do cliente que realizou a contratação.")
    private Long clienteId;

    @Schema(description = "ID do cartão que foi contratado.")
    private Long cartaoId;
}