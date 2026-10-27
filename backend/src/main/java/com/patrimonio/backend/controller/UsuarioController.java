package com.patrimonio.backend.controller;

import com.patrimonio.backend.model.Usuario;
import com.patrimonio.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Integer id) {
        return ResponseEntity.of(usuarioRepository.findById(id));
    }

    // Mismas reglas que la app: email en minúsculas, no se repite y ENCARGADO no se registra
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        if ("ENCARGADO".equals(usuario.getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        usuario.setUsuarioId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    // Recibe {"email": "...", "password": "..."}; 401 si no existe o la clave está mal
    // shortcut: contraseña en texto plano; en la etapa 3 se guarda con BCrypt (Spring Security)
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> datos) {
        String email = datos.getOrDefault("email", "").trim().toLowerCase();
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isEmpty() || !usuario.get().getPassword().equals(datos.get("password"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(usuario.get());
    }
}
