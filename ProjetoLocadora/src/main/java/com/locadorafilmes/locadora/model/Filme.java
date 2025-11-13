package com.locadorafilmes.locadora.model;

import com.locadorafilmes.locadora.enums.StatusFilme;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}