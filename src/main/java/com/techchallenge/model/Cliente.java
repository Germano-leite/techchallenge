package com.techchallenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity //Mapeia a classe como uma tabela para o BD relacional
@Getter
@Setter 
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;

}

