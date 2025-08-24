package com.techchallenge.service;

import com.techchallenge.domain.dto.ContratacaoRequest;
import com.techchallenge.domain.dto.ContratacaoResponse;
import com.techchallenge.exception.ResourceNotFoundException; // Usando a exceção customizada
import com.techchallenge.model.*;
import com.techchallenge.repository.CartaoRepository;
import com.techchallenge.repository.ClienteRepository;
import com.techchallenge.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratacaoService {

    @Autowired
    private ContratacaoRepository contratacaoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    public List<ContratacaoResponse> listarTodas() {
        return contratacaoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ContratacaoResponse> buscarPorId(Long id) {
        return contratacaoRepository.findById(id).map(this::toResponse);
    }
 
    // Este método será otimizado no próximo passo
    public List<ContratacaoResponse> listarPorCliente(Long clienteId) {
        // Por enquanto, mantemos a lógica, mas retornando DTO
        return contratacaoRepository.findAll().stream()
                .filter(c -> c.getCliente().getId().equals(clienteId))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public ContratacaoResponse contratarCartao(ContratacaoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + request.getClienteId() + " não encontrado"));

        Cartao cartao = cartaoRepository.findById(request.getCartaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Cartão com ID " + request.getCartaoId() + " não encontrado"));
                
        Contratacao novaContratacao = new Contratacao();
        novaContratacao.setCliente(cliente);
        novaContratacao.setCartao(cartao);
        novaContratacao.setStatus(StatusContratacao.ATIVO); // Status padrão

        // A data e o número do cartão serão gerados via @PrePersist na entidade
        
        Contratacao contratacaoSalva = contratacaoRepository.save(novaContratacao);
        return toResponse(contratacaoSalva);
    }

    public ContratacaoResponse atualizarStatus(Long id, StatusContratacao novoStatus) {
        Contratacao contratacao = contratacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contratação com ID " + id + " não encontrada"));
        
        contratacao.setStatus(novoStatus);
        Contratacao contratacaoAtualizada = contratacaoRepository.save(contratacao);
        return toResponse(contratacaoAtualizada);
    }

    public void deletar(Long id) {
        if (!contratacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contratação com ID " + id + " não encontrada");
        }
        contratacaoRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Contratacao para um ContratacaoResponse DTO.
     */
    private ContratacaoResponse toResponse(Contratacao contratacao) {
        ContratacaoResponse response = new ContratacaoResponse();
        response.setId(contratacao.getId());
        response.setClienteId(contratacao.getCliente().getId());
        response.setClienteNome(contratacao.getCliente().getNome());
        response.setCartaoId(contratacao.getCartao().getId());
        response.setCartaoNome(contratacao.getCartao().getNome());
        response.setNumeroCartao(contratacao.getNumeroCartao()); // Ajuste aqui 
        response.setDataContratacao(contratacao.getDataContratacao());
        response.setStatus(contratacao.getStatus().name());
        return response;
    }
}

/*package com.techchallenge.service;
 
import com.techchallenge.model.*;
import com.techchallenge.repository.CartaoRepository;
import com.techchallenge.repository.ClienteRepository;
import com.techchallenge.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratacaoService {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public List<Contratacao> listarTodas() {
        return contratacaoRepository.findAll();
    }

    public Optional<Contratacao> buscarPorId(Long id) {
        return contratacaoRepository.findById(id);
    }
 
    public List<Contratacao> listarPorCliente(Long clienteId) {
        return contratacaoRepository.findAll().stream()
                .filter(c -> c.getCliente().getId().equals(clienteId))
                .toList();
    }

    private String gerarNumeroCartao(String bandeira, String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // remove pontos e traços
        
        String bin;
        switch (bandeira.toUpperCase()) {
            case "VISA": bin = "400000"; break;
            case "MASTERCARD": bin = "510000"; break;
            case "ELO": bin = "636300"; break;
            default: bin = "999999"; // fallback
        }
    
        // Usa apenas os 9 primeiros dígitos do CPF
        String parcial = bin + cpf.substring(0, 9);
    
        // Calcula dígito verificador com Luhn
        int digito = calcularLuhn(parcial);
    
        return parcial + digito;
    }
    
    //Essa função foi gerada por IA a pedido.
    private int calcularLuhn(String numero) {
        int soma = 0;
        boolean alternar = true;
    
        for (int i = numero.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(numero.substring(i, i + 1));
            if (alternar) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            soma += n;
            alternar = !alternar;
        }
        return (10 - (soma % 10)) % 10;
    }
    

    public Contratacao contratarCartao(Long clienteId, Long cartaoId, StatusContratacao status) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
                     
        String numeroCartao = gerarNumeroCartao(cartao.getBandeira().toString(), cliente.getCpf());
                
        Contratacao contratacao = new Contratacao();
        contratacao.setCliente(cliente);
        contratacao.setCartao(cartao);
        contratacao.setDataContratacao(LocalDate.now());
        contratacao.setStatus(status);
        contratacao.setNumeroCartao(numeroCartao);

        return contratacaoRepository.save(contratacao);
    }


    public Contratacao atualizarStatus(Long id, StatusContratacao novoStatus) {
        return contratacaoRepository.findById(id).map(c -> {
            c.setStatus(novoStatus);
            return contratacaoRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Contratação não encontrada"));
    }

    public void deletar(Long id) {
        contratacaoRepository.deleteById(id);
    }
}
*/