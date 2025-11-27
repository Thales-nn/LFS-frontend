package com.locadorafilmes.locadora.model;

import com.locadorafilmes.locadora.enums.StatusFilme;
import jakarta.persistence.*;

@Entity
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String diretor;
    private String genero;
    private Integer anoLancamento;

    @Column(name = "valor_locacao")
    private Double valorLocacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusFilme statusFilme;

    private String sinopse;

    public Filme() {
    }

    public Filme(Long id, String titulo, String diretor, String genero, Integer anoLancamento, Double valorLocacao, StatusFilme statusFilme, String sinopse) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.valorLocacao = valorLocacao;
        this.statusFilme = statusFilme;
        this.sinopse = sinopse;
    }

    // Getters e Setters Manuais
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Double getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(Double valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public StatusFilme getStatusFilme() {
        return statusFilme;
    }

    public void setStatusFilme(StatusFilme statusFilme) {
        this.statusFilme = statusFilme;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}