package com.locadorafilmes.locadora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locadorafilmes.locadora.model.Locacao;
import com.locadorafilmes.locadora.repository.LocacaoRepository;
import com.locadorafilmes.locadora.service.dto.LocacaoFormData;

@Service
public class LocacaoService {

    private final LocacaoRepository repository;
    private final FilmeService filmeService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    
    @Autowired
    public LocacaoService(LocacaoRepository repository, FilmeService filmeService, ClienteService clienteService, UsuarioService usuarioService) {
        this.repository = repository;
        this.filmeService = filmeService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    public LocacaoFormData prepararDadosFormulario() {
        LocacaoFormData data = new LocacaoFormData();
        data.setFilmes(filmeService.listarTodos());
        data.setClientes(clienteService.listarTodos());
        data.setUsuarios(usuarioService.listarTodos());
        return data;
    }
    
    public Locacao salvar(Locacao locacao) {
        return repository.save(locacao);
    }

    public List<Locacao> listarTodos() {
        return repository.findAll();
    }

    public Locacao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Locacao> findAllComRelacionamentos() {
        return repository.findAllComRelacionamentos();
    }
    
    public List<Locacao> buscarLocacoes(Long id, String filme, String cliente) {
        return repository.findByFiltros(id, filme, cliente);
    }

    public List<Locacao> buscarUltimasLocacoes() {
        return repository.findTop5ByOrderByIdDesc();
    }

    public long contarTotal() {
        return repository.count();
    }
}