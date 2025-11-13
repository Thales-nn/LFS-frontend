package com.locadorafilmes.locadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.locadorafilmes.locadora.model.Locacao;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long>{

    @Query("SELECT loc FROM Locacao loc " +
           "JOIN FETCH loc.cliente " +
           "JOIN FETCH loc.filme " +
           "LEFT JOIN FETCH loc.pagamentos")
    List<Locacao> findAllComRelacionamentos();
    
    @Query("SELECT loc FROM Locacao loc " +
           "WHERE (:id IS NULL OR loc.id = :id) " +
           "AND (:filme IS NULL OR CAST(loc.filme.id AS string) LIKE CONCAT('%', :filme, '%')) " +
           "AND (:cliente IS NULL OR LOWER(loc.cliente.nome) LIKE LOWER(CONCAT('%', :cliente, '%'))) ")
    List<Locacao> findByFiltros(
        @Param("id") Long id,
        @Param("filme") String filme,
        @Param("cliente") String cliente);
        
    List<Locacao> findTop5ByOrderByIdDesc();
}