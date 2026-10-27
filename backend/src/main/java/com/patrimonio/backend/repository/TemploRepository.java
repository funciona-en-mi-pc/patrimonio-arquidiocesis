package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.Templo;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface TemploRepository extends JpaRepository<Templo, Integer> {}
