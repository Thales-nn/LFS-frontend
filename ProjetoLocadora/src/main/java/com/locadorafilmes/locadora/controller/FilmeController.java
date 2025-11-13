package com.locadorafilmes.locadora.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.locadorafilmes.locadora.model.Filme;
import com.locadorafilmes.locadora.service.GerarRelatorioService;
import com.locadorafilmes.locadora.service.FilmeService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/filmes")
public class FilmeController {
    private final FilmeService service;
    private final GerarRelatorioService relatorioService;
    
    public FilmeController(FilmeService service, GerarRelatorioService relatorioService) {
        this.service = service;
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", service.listarTodos());
        model.addAttribute("urlRelatorio", "/filmes/relatorio");
        return "filmes/pesquisaFilmes";
    }

    @GetMapping("/novo")
    public String formularioNovoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "filmes/cadastroAtualizarFilme";
    }

    @PostMapping
    public String salvarFilme(@ModelAttribute("filme") Filme filme) {
        service.salvar(filme);
        return "redirect:/filmes";
    }

    @GetMapping("/editar/{id}")
    public String editarFilme(@PathVariable Long id, Model model) {
        model.addAttribute("filme", service.buscarPorId(id));
        return "filmes/cadastroAtualizarFilme";
    }

    @GetMapping("/excluir/{id}")
    public String excluirFilme(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Filme excluído com sucesso!");
        } catch (Exception e) {
            // Captura o erro de integridade referencial
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este filme pois ele está vinculado a locações registradas.");
        }
        return "redirect:/filmes";
    }
    
    @GetMapping("/relatorio")
    public void gerarRelatorio(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-filmes.pdf");
        List<Filme> lista = service.listarTodos();
        relatorioService.gerarPdfFilmes(response, lista);
    }
}