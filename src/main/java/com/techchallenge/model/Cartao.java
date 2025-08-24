package com.techchallenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cartão não pode ser vazio.")
    private String nome;

    @NotNull(message = "O tipo do cartão não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    @NotNull(message = "A anuidade não pode ser nula.")
    @DecimalMin(value = "0.0", message = "A anuidade deve ser maior que zero ou igual.")
    private BigDecimal anuidade;

    @NotNull(message = "A bandeira do cartão não pode ser nula.") 
    @Enumerated(EnumType.STRING)
    private Bandeira bandeira;

}
