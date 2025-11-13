package com.locadorafilmes.locadora.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.locadorafilmes.locadora.model.Locacao;
import com.locadorafilmes.locadora.service.LocacaoService;
import com.locadorafilmes.locadora.service.GerarRelatorioService;

import java.security.Principal;
import com.locadorafilmes.locadora.model.Usuario;
import com.locadorafilmes.locadora.enums.Role;
import com.locadorafilmes.locadora.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/locacoes")
public class LocacaoController {

    private final LocacaoService locacaoService;
    private final GerarRelatorioService relatorioService;
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public LocacaoController(LocacaoService locacaoService, GerarRelatorioService relatorioService, UsuarioRepository usuarioRepository) {
        this.locacaoService = locacaoService;
        this.relatorioService = relatorioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarLocacoes(Model model) {
        model.addAttribute("locacoes", locacaoService.listarTodos());
        model.addAttribute("urlRelatorio", "/locacoes/relatorio");
        return "locacao/pesquisaLocacoes";
    }

    @GetMapping("/novo")
    public String novaLocacao(Model model) {
        model.addAttribute("formData", locacaoService.prepararDadosFormulario());
        model.addAttribute("locacao", new Locacao());
        return "locacao/cadastroAtualizarLocacao";
    }

    @PostMapping("/salvar")
    public String salvarLocacao(@ModelAttribute Locacao locacao, Principal principal) {
        Usuario usuarioLogado = usuarioRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));
        
        if (usuarioLogado.getRole() == Role.FUNCIONARIO) {
            locacao.setUsuario(usuarioLogado);
        }
        
        locacaoService.salvar(locacao);
        return "redirect:/locacoes";
    }

    @GetMapping("/editar/{id}")
    public String editarLocacao(@PathVariable Long id, Model model) {
        model.addAttribute("formData", locacaoService.prepararDadosFormulario());
        model.addAttribute("locacao", locacaoService.buscarPorId(id));
        return "locacao/cadastroAtualizarLocacao";
    }

    @GetMapping("/excluir/{id}")
    public String excluirLocacao(@PathVariable Long id) {
        locacaoService.deletar(id);
        return "redirect:/locacoes";
    }
    
    @GetMapping("/relatorio")
    public void gerarRelatorio(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-locacoes.pdf");
        List<Locacao> lista = locacaoService.listarTodos();
        relatorioService.gerarPdfLocacoes(response, lista);
    }
    
    @GetMapping("/buscar")
    public String buscarLocacoes(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String filme,
        @RequestParam(required = false) String cliente,
        Model model) {
            
        List<Locacao> locacoes = locacaoService.buscarLocacoes(id, filme, cliente);
        model.addAttribute("locacoes", locacoes);
        return "locacao/pesquisaLocacoes";
    }
}