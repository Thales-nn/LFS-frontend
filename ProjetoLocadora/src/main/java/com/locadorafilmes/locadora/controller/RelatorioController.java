package com.locadorafilmes.locadora.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locadorafilmes.locadora.model.Locacao;
import com.locadorafilmes.locadora.model.Filme;
import com.locadorafilmes.locadora.model.Pagamento;
import com.locadorafilmes.locadora.service.LocacaoService;
import com.locadorafilmes.locadora.service.GerarRelatorioService;
import com.locadorafilmes.locadora.service.FilmeService;
import com.locadorafilmes.locadora.service.PagamentoService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {
	
	private final GerarRelatorioService pdfGeneratorService;
    private final FilmeService filmeService;
    private final PagamentoService pagamentoService;
    private final LocacaoService locacaoService;
    
    public RelatorioController(GerarRelatorioService pdfGeneratorService, FilmeService filmeService,
			PagamentoService pagamentoService, LocacaoService locacaoService) {
		this.pdfGeneratorService = pdfGeneratorService;
		this.filmeService = filmeService;
		this.pagamentoService = pagamentoService;
		this.locacaoService = locacaoService;
	}
    
    @GetMapping
    public String paginaRelatorios() {
        return "relatorio/opcoes";  
    }

    @GetMapping("/filmes")
    public void gerarRelatorioFilmes(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-filmes.pdf");
        
        List<Filme> filmes = filmeService.listarTodos();
        pdfGeneratorService.gerarPdfFilmes(response, filmes);
    }

    @GetMapping("/locacoes")
    public void gerarRelatorioLocacoes(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-locacoes.pdf");
        
        List<Locacao> locacoes = locacaoService.listarTodos();
        pdfGeneratorService.gerarPdfLocacoes(response, locacoes);
    }

    @GetMapping("/pagamentos")
    public void gerarRelatorioPagamentos(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-pagamentos.pdf");
        
        List<Pagamento> pagamentos = pagamentoService.listarTodos();
        pdfGeneratorService.gerarPdfPagamentos(response, pagamentos);
    }
}