package com.patrimonio.backend.repository;

import com.patrimonio.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository ya trae findAll, findById, save y deleteById
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
