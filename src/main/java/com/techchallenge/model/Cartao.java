package com.techchallenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    private BigDecimal anuidade;

    @Enumerated(EnumType.STRING)
    private Bandeira bandeira;

}
