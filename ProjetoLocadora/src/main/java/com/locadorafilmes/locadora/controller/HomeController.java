package com.locadorafilmes.locadora.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.locadorafilmes.locadora.model.Locacao;
import com.locadorafilmes.locadora.model.Usuario;
import com.locadorafilmes.locadora.repository.UsuarioRepository;
import com.locadorafilmes.locadora.service.ClienteService;
import com.locadorafilmes.locadora.service.FilmeService;
import com.locadorafilmes.locadora.service.LocacaoService;
import com.locadorafilmes.locadora.service.PagamentoService;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired private LocacaoService locacaoService;
    @Autowired private FilmeService filmeService;
    @Autowired private ClienteService clienteService;
    @Autowired private PagamentoService pagamentoService;

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
        
        carregarDadosDashboard(model);

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
    public String dashboard(Model model) {
        carregarDadosDashboard(model);
    	return "dashboard";
    }
    
    private void carregarDadosDashboard(Model model) {
        List<Locacao> todasLocacoes = locacaoService.listarTodos();
        
        model.addAttribute("totalLocacoes", todasLocacoes.size());
        model.addAttribute("filmesDisponiveis", filmeService.listarTodos().size());
        model.addAttribute("clientesAtivos", clienteService.listarTodos().size());
        model.addAttribute("totalPagamentos", pagamentoService.listarTodos().size());
        
        List<Locacao> ultimas = todasLocacoes.stream()
            .sorted(Comparator.comparing(Locacao::getId).reversed())
            .limit(5)
            .collect(Collectors.toList());
            
        model.addAttribute("ultimasLocacoes", ultimas);
    }
}