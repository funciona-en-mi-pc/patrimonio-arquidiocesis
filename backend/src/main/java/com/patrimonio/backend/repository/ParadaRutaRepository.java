package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.ParadaRuta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface ParadaRutaRepository extends JpaRepository<ParadaRuta, Integer> {
    List<ParadaRuta> findByRutaIdOrderByOrden(Integer rutaId);
}
