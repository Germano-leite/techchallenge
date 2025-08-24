package com.techchallenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID; // Importação necessária para gerar o número

@Entity
@Data
public class Contratacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O cliente é obrigatório para a contratação.")
    @ManyToOne(fetch = FetchType.LAZY) // Opcional: Melhora a performance
    @JoinColumn(name = "cliente_id") // Boa prática: define o nome da coluna no BD
    private Cliente cliente;

    @NotNull(message = "O cartão é obrigatório para a contratação.")
    @ManyToOne(fetch = FetchType.LAZY) // Opcional: Melhora a performance
    @JoinColumn(name = "cartao_id") // Boa prática: define o nome da coluna no BD
    private Cartao cartao;

    @PastOrPresent(message = "A data da contratação não pode ser no futuro.")
    private LocalDate dataContratacao;

    @NotNull(message = "O status da contratação é obrigatório.")
    @Enumerated(EnumType.STRING)
    private StatusContratacao status;

    private String numeroCartao;

    /**
     * Este método é chamado automaticamente pelo JPA antes de
     * a entidade ser salva no banco de dados pela primeira vez.
     * Ideal para gerar valores padrão.
     */
    @PrePersist
    public void gerarDadosPadrao() {
        // Define a data da contratação como a data atual se ela não for fornecida
        if (this.dataContratacao == null) {
            this.dataContratacao = LocalDate.now();
        }

        // Gera um número de cartão simulado e único
        if (this.numeroCartao == null) {
            // Lógica de simulação: um UUID de 16 caracteres sem hifens.
            this.numeroCartao = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        }
    }
}
