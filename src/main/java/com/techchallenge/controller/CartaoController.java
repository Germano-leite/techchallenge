package com.techchallenge.controller;

import com.techchallenge.model.Cartao;
import com.techchallenge.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @GetMapping
    public List<Cartao> listarTodos() {
        return cartaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> buscarPorId(@PathVariable Long id) {
        return cartaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cartao criar(@RequestBody Cartao cartao) {
        return cartaoService.salvar(cartao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cartao> atualizar(@PathVariable Long id, @RequestBody Cartao cartao) {
        try {
            return ResponseEntity.ok(cartaoService.atualizar(id, cartao));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cartaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
