package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.AsignacionGuia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface AsignacionGuiaRepository extends JpaRepository<AsignacionGuia, Integer> {
    List<AsignacionGuia> findByGuiaId(Integer guiaId);
}
