package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.DisponibilidadHoraria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface DisponibilidadHorariaRepository extends JpaRepository<DisponibilidadHoraria, Integer> {
    List<DisponibilidadHoraria> findByTemploId(Integer temploId);
}
