package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.SalidaPedagogica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface SalidaPedagogicaRepository extends JpaRepository<SalidaPedagogica, Integer> {
    List<SalidaPedagogica> findByColegioId(Integer colegioId);
}
