package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.EventoCultural;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface EventoCulturalRepository extends JpaRepository<EventoCultural, Integer> {
    List<EventoCultural> findByTemploIdOrderByFechaHora(Integer temploId);
}
