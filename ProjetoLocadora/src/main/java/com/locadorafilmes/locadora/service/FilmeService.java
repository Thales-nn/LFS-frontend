package com.locadorafilmes.locadora.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locadorafilmes.locadora.model.Filme;
import com.locadorafilmes.locadora.repository.FilmeRepository;

@Service
public class FilmeService {

    private final FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> listarTodos() {
        return repository.findAll();
    }

    public Filme buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com o ID: " + id));
    }

    public Filme salvar(Filme filme) {
        return repository.save(filme);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}