package com.locadorafilmes.locadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locadorafilmes.locadora.enums.StatusFilme;
import com.locadorafilmes.locadora.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    List<Filme> findByStatusFilme(StatusFilme statusFilme);
    
}