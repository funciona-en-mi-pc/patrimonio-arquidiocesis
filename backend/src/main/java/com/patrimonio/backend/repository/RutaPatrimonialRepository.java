package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.RutaPatrimonial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface RutaPatrimonialRepository extends JpaRepository<RutaPatrimonial, Integer> {
    List<RutaPatrimonial> findByUsuarioCreadorId(Integer usuarioCreadorId);
}
