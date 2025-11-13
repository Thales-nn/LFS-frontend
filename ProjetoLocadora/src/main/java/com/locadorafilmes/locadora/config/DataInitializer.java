package com.locadorafilmes.locadora.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.locadorafilmes.locadora.enums.Role;
import com.locadorafilmes.locadora.model.Usuario;
import com.locadorafilmes.locadora.repository.UsuarioRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Usuário Administrador
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("Admin");
            admin.setUsername("admin");                   
            admin.setSenha(passwordEncoder.encode("admin123")); 
            admin.setRole(Role.ADMINISTRADOR);            
            usuarioRepository.save(admin);
        }
        
        // Usuário Funcionário 1
        if (usuarioRepository.findByUsername("roberto12").isEmpty()) {
            Usuario funcionario = new Usuario();
            funcionario.setNome("Roberto");
            funcionario.setUsername("roberto12");                   
            funcionario.setSenha(passwordEncoder.encode("roberto24")); 
            funcionario.setRole(Role.FUNCIONARIO); // <-- CORRIGIDO        
            usuarioRepository.save(funcionario);
        }

        // Usuário Funcionário 2
        if (usuarioRepository.findByUsername("maria").isEmpty()) {
            Usuario funcionario = new Usuario();
            funcionario.setNome("Maria Helena");
            funcionario.setUsername("maria");                   
            funcionario.setSenha(passwordEncoder.encode("maria123")); 
            funcionario.setRole(Role.FUNCIONARIO); // <-- CORRIGIDO          
            usuarioRepository.save(funcionario);
        }
    }
}