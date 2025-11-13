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

import com.locadorafilmes.locadora.model.Cliente;
import com.locadorafilmes.locadora.service.GerarRelatorioService;
import com.locadorafilmes.locadora.service.ClienteService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;
    private final GerarRelatorioService relatorioService;
    
    public ClienteController(ClienteService service, GerarRelatorioService relatorioService) {
        this.service = service;
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", service.listarTodos());
        model.addAttribute("urlRelatorio", "/clientes/relatorio");
        return "clientes/pesquisaClientes";
    }

    @GetMapping("/novo")
    public String formularioNovoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/cadastroAtualizarCliente";
    }

    @PostMapping
    public String salvarCliente(@ModelAttribute("cliente") Cliente cliente) {
        service.salvar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id));
        return "clientes/cadastroAtualizarCliente";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este cliente pois ele possui locações ou pagamentos registrados.");
        }
        return "redirect:/clientes";
    }
    
    @GetMapping("/relatorio")
    public void gerarRelatorioClientes(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-clientes.pdf");
        List<Cliente> lista = service.listarTodos();
        relatorioService.gerarPdfClientes(response, lista);
    }
}