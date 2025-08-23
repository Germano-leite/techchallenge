package com.techchallenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Entity //Define que a classe deve ser mapeada no BD
@Getter
@Setter // GETTERS SETTERS Trocar anotacao futuramente
public class Contratacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Cartao cartao;

    private LocalDate dataContratacao;

    @Enumerated(EnumType.STRING)
    private StatusContratacao status;

    private String numeroCartao;
}
