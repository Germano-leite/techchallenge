package com.techchallenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data; // Usando @Data para simplicidade (inclui Getter, Setter, etc.)
import java.time.LocalDate;

@Data
@Schema(description = "Dados de uma contratação retornados pela API.")
public class ContratacaoResponse {

    @Schema(description = "ID único da contratação.")
    private Long id;

    @Schema(description = "ID do cliente que realizou a contratação.")
    private Long clienteId;

    @Schema(description = "Nome do cliente que realizou a contratação.")
    private String clienteNome; // Campo adicionado

    @Schema(description = "ID do cartão que foi contratado.")
    private Long cartaoId;

    @Schema(description = "Nome do cartão que foi contratado.")
    private String cartaoNome; // Campo adicionado

    @Schema(description = "Número do cartão gerado na contratação.")
    private String numeroCartao;

    @Schema(description = "Data em que a contratação foi realizada.")
    private LocalDate dataContratacao;

    @Schema(description = "Status atual da contratação.", example = "ATIVO")
    private String status;
}