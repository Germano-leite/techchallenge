package com.techchallenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import org.hibernate.validator.constraints.br.CPF;
import lombok.Data;

import java.time.LocalDate;


@Entity //Mapeia a classe como uma tabela para o BD relacional
@Data 
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O CPF não pode ser vazio.")
    //@CPF(message = "CPF inválido.") // Usa a validação específica de CPF - Desabilitado para facilitar testes
    @Column(unique = true) // Garante que o CPF seja único no banco de dados   
    private String cpf;

    @NotBlank(message = "O e-mail não pode ser vazio.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotNull(message = "A data de nascimento não pode ser nula.")
    private LocalDate dataNascimento;

}