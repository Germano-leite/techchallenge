package com.techchallenge.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro de um novo cartão.")
public class CartaoRequest {

    @NotBlank(message = "O nome não pode estar vazio.")
    @Schema(description = "Nome do cartão (ex: Cartão Platinum).", example = "Cartão Platinum")
    private String nome;

    @NotBlank(message = "O tipo não pode estar vazio.")
    @Schema(description = "Tipo do cartão (Crédito ou Débito).", example = "CREDITO")
    private String tipo;

    @NotNull(message = "A anuidade não pode estar vazia.")
    @Schema(description = "Valor da anuidade em reais.", example = "350.00")
    private BigDecimal anuidade;

    @NotBlank(message = "A bandeira não pode estar vazia.")
    @Schema(description = "Bandeira do cartão (ex: Visa, MasterCard).", example = "MASTERCARD")
    private String bandeira;
}
