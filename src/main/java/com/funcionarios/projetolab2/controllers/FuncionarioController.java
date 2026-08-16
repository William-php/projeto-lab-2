package com.funcionarios.projetolab2.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funcionarios.projetolab2.models.FuncionarioModel;
import com.funcionarios.projetolab2.repositories.FuncionarioRepository;

@RestController
@RequestMapping("/funcionarios")
@CrossOrigin("*")
public class FuncionarioController {

    // Injeção de dependência do repositório
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // ==========================================
    // 1. CADASTRAR FUNCIONÁRIO (POST)
    // ==========================================
    @PostMapping
    public ResponseEntity<FuncionarioModel> cadastrar(@RequestBody FuncionarioModel funcionario) {
        FuncionarioModel funcionarioSalvo = funcionarioRepository.save(funcionario);
        // Retorna status 201 (Created) quando salva com sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
    }

    // ==========================================
    // 2. LISTAR TODOS OS FUNCIONÁRIOS (GET)
    // ==========================================
    @GetMapping
    public ResponseEntity<List<FuncionarioModel>> listarTodos() {
        List<FuncionarioModel> funcionarios = funcionarioRepository.findAll();
        return ResponseEntity.ok(funcionarios);
    }

    // ==========================================
    // 3. BUSCAR FUNCIONÁRIO POR ID (GET)
    // ==========================================
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioModel> buscarPorId(@PathVariable Integer id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> ResponseEntity.ok(funcionario))
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não achar
    }

    // ==========================================
    // 4. ATUALIZAR FUNCIONÁRIO (PUT)
    // ==========================================
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioModel> atualizar(@PathVariable Integer id, @RequestBody FuncionarioModel funcionarioAtualizado) {
        return funcionarioRepository.findById(id)
                .map(funcionarioExistente -> {
                    // Atualiza os dados permitidos
                    funcionarioExistente.setNome(funcionarioAtualizado.getNome());
                    funcionarioExistente.setSobrenome(funcionarioAtualizado.getSobrenome());
                    funcionarioExistente.setDataNascimento(funcionarioAtualizado.getDataNascimento());
                    funcionarioExistente.setDataContratacao(funcionarioAtualizado.getDataContratacao());
                    funcionarioExistente.setDataDemissao(funcionarioAtualizado.getDataDemissao());
                    
                    // Salva a alteração
                    FuncionarioModel salvo = funcionarioRepository.save(funcionarioExistente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================================
    // 5. DELETAR FUNCIONÁRIO (DELETE)
    // ==========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 (No Content) após deletar
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se tentar deletar um ID que não existe
    }
}