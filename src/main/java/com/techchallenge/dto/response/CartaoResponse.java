package com.techchallenge.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
//import java.time.LocalDateTime;

@Data
@Schema(description = "Dados de um cartão retornados pela API.")
public class CartaoResponse {

    @Schema(description = "ID único do cartão.")
    private Long id;

    @Schema(description = "Nome do cartão.")
    private String nome;

    @Schema(description = "Tipo do cartão.")
    private String tipo;

    @Schema(description = "Valor da anuidade.")
    private BigDecimal anuidade;

    @Schema(description = "Bandeira do cartão.")
    private String bandeira;
}