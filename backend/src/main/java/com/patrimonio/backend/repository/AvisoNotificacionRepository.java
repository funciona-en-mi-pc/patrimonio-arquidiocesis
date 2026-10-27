package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.AvisoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface AvisoNotificacionRepository extends JpaRepository<AvisoNotificacion, Integer> {
    List<AvisoNotificacion> findByTemploId(Integer temploId);
}
