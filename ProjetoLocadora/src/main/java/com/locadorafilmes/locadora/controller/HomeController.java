package com.locadorafilmes.locadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.locadorafilmes.locadora.model.Usuario;
import com.locadorafilmes.locadora.repository.UsuarioRepository;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        String login = authentication.getName(); 

        Usuario usuario = usuarioRepository.findByUsername(login).orElse(null); 
        String nome = usuario != null ? usuario.getNome() : login; 
        
        String role = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("ROLE_USUARIO");

        String funcao = switch (role) {
            case "ROLE_ADMINISTRADOR" -> "Administrador";
            case "ROLE_FUNCIONARIO" -> "Funcionário";
            default -> "Usuário";
        };

        model.addAttribute("userName", nome);
        model.addAttribute("userRole", funcao);

        return "home";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
    	return "dashboard";
    }
}