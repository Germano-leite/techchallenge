package com.techchallenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.Setter;
import lombok.Data;
import java.time.LocalDate;


@Entity //Define que a classe deve ser mapeada no BD
//@Getter
//@Setter
@Data
public class Contratacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O cliente não pode ser nulo.")
    @ManyToOne
    private Cliente cliente;

    @NotNull(message = "O cartão não pode ser nulo.")
    @ManyToOne
    private Cartao cartao;

    @NotNull(message = "A data de contratação não pode ser nula.")
    private LocalDate dataContratacao;

    @NotNull(message = "O status da contratação não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    private StatusContratacao status;

    private String numeroCartao;
}
