package com.locadorafilmes.locadora.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locadorafilmes.locadora.enums.Role;
import com.locadorafilmes.locadora.model.Usuario;
import com.locadorafilmes.locadora.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	public List<Usuario> listarTodos() {
        return repository.findAll();
    }
	
	public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com o ID: " + id));
    }
	
	public List<Usuario> findByRole(Role role) {
        return repository.findByRole(role);
    }
}