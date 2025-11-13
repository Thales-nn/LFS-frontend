package com.locadorafilmes.locadora.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locadorafilmes.locadora.enums.Role;
import com.locadorafilmes.locadora.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    
    List<Usuario> findByRole(Role role);

    Optional<Usuario> findByEmail(String login);
}