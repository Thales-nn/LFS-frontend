package com.locadorafilmes.locadora.service.dto;

import java.util.List;

import com.locadorafilmes.locadora.model.Filme;
import com.locadorafilmes.locadora.model.Cliente;
import com.locadorafilmes.locadora.model.Usuario;

public class LocacaoFormData {
    private List<Filme> filmes;
    private List<Cliente> clientes;
    private List<Usuario> usuarios;
    
    public LocacaoFormData() {
    }

    public LocacaoFormData(List<Filme> filmes, List<Cliente> clientes, List<Usuario> usuarios) {
        this.filmes = filmes;
        this.clientes = clientes;
        this.usuarios = usuarios;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}