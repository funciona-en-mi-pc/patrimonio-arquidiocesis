package com.patrimonio.backend.controller;

import com.patrimonio.backend.model.AvisoNotificacion;
import com.patrimonio.backend.model.EventoCultural;
import com.patrimonio.backend.repository.AvisoNotificacionRepository;
import com.patrimonio.backend.repository.EventoCulturalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

// Lo que publica el encargado del templo: eventos de la agenda y avisos
@RestController
@RequestMapping("/api")
public class EventoController {

    private final EventoCulturalRepository eventoRepository;
    private final AvisoNotificacionRepository avisoRepository;

    public EventoController(EventoCulturalRepository eventoRepository, AvisoNotificacionRepository avisoRepository) {
        this.eventoRepository = eventoRepository;
        this.avisoRepository = avisoRepository;
    }

    @GetMapping("/eventos")
    public List<EventoCultural> listarEventos() {
        return eventoRepository.findAll();
    }

    @PostMapping("/eventos")
    public ResponseEntity<EventoCultural> crearEvento(@RequestBody EventoCultural evento) {
        evento.setEventoId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoRepository.save(evento));
    }

    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Integer id) {
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/avisos")
    public List<AvisoNotificacion> listarAvisos() {
        return avisoRepository.findAll();
    }

    @PostMapping("/avisos")
    public ResponseEntity<AvisoNotificacion> publicarAviso(@RequestBody AvisoNotificacion aviso) {
        aviso.setAvisoId(null);
        aviso.setFechaHora(LocalDateTime.now().toString().substring(0, 16));
        aviso.setSincronizado(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(avisoRepository.save(aviso));
    }
}
