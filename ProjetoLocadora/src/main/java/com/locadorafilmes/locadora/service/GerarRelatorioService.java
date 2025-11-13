package com.locadorafilmes.locadora.service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.locadorafilmes.locadora.model.Locacao;
import com.locadorafilmes.locadora.model.Filme;
import com.locadorafilmes.locadora.model.Cliente;
import com.locadorafilmes.locadora.model.Pagamento;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class GerarRelatorioService {

    public void gerarPdfLocacoes(HttpServletResponse response, List<Locacao> lista) {
        try {
            gerarPdf(
                response.getOutputStream(),
                "Relatório de Locações",
                new String[]{"ID","Data de Locação","Data de Devolução","Valor","Cliente (ID)","Filme (ID)","Funcionário (ID)"},
                lista.stream().map(l -> new String[]{
                    String.valueOf(l.getId()),
                    formatDate(l.getDataLocacao()),
                    formatDate(l.getDataDevolucao()),
                    formatCurrency(l.getValor()),
                    l.getCliente() != null ? String.valueOf(l.getCliente().getId()) : "-",
                    l.getFilme() != null ? String.valueOf(l.getFilme().getId()) : "-",
                    l.getUsuario() != null ? String.valueOf(l.getUsuario().getId()) : "-"
                }).toList()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarPdfFilmes(HttpServletResponse response, List<Filme> lista) {
        try {
            gerarPdf(
                response.getOutputStream(),
                "Relatório de Filmes",
                new String[]{"ID","Título","Diretor","Gênero","Ano","Valor da Locação","Status"},
                lista.stream().map(f -> new String[]{
                    String.valueOf(f.getId()),
                    f.getTitulo(),
                    f.getDiretor(),
                    f.getGenero(),
                    String.valueOf(f.getAnoLancamento()),
                    formatCurrency(f.getValorLocacao()),
                    f.getStatusFilme() != null ? f.getStatusFilme().toString() : "-"
                }).toList()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void gerarPdfClientes(HttpServletResponse response, List<Cliente> lista) {
        try {
            gerarPdf(response.getOutputStream(), "Relatório de Clientes",
                new String[]{"ID", "Nome", "Email", "CPF", "Telefone", "Categoria"},
                lista.stream().map(c -> new String[]{
                        String.valueOf(c.getId()),
                        c.getNome(),
                        c.getEmail(),
                        c.getCpf(),
                        c.getTelefone(),
                        c.getCategoria().toString()
                    }).toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarPdfPagamentos(HttpServletResponse response, List<Pagamento> lista) {
	    try {
	        gerarPdf(
	            response.getOutputStream(),
	            "Relatório de Pagamentos",
	            new String[]{"ID","Valor","Data Vencimento","Data Pagamento","Status","Forma de Pagamento","ID Locação"},
	            lista.stream().map(p -> new String[]{
	                String.valueOf(p.getId()),
	                formatCurrency(p.getValor()),
	                formatDate(p.getDataVencimento()),
	                formatDate(p.getDataPagamento()),
	                (p.getStatus() != null && p.getStatus()) ? "Pago" : "Vencido",
	                p.getFormaDePagamento() != null ? p.getFormaDePagamento().toString() : "-",
	                p.getLocacao() != null ? String.valueOf(p.getLocacao().getId()) : "-"
	            }).toList()
	        );
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    private void gerarPdf(OutputStream out, String titulo, String[] headers, List<String[]> rows) {
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, out);
            documento.open();
            documento.add(new Paragraph(titulo));
            documento.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(headers.length);
            for (String header : headers) {
                tabela.addCell(new Paragraph(header));
            }
            for (String[] row : rows) {
                for (String cell : row) {
                    tabela.addCell(new Paragraph(cell));
                }
            }
            documento.add(tabela);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }

    private String formatDate(java.util.Date date) {
        if (date == null) return "N/A";
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    private String formatDate(java.time.LocalDate date) {
        if (date == null) return "N/A";
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String formatCurrency(double value) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(value);
    }
}