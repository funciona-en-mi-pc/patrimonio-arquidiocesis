package com.patrimonio.backend.controller;

import com.patrimonio.backend.model.AvisoNotificacion;
import com.patrimonio.backend.model.DisponibilidadHoraria;
import com.patrimonio.backend.model.EventoCultural;
import com.patrimonio.backend.model.Templo;
import com.patrimonio.backend.repository.AvisoNotificacionRepository;
import com.patrimonio.backend.repository.DisponibilidadHorariaRepository;
import com.patrimonio.backend.repository.EventoCulturalRepository;
import com.patrimonio.backend.repository.TemploRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Catálogo: lo administra la Arquidiócesis, por eso aquí solo se consulta
@RestController
@RequestMapping("/api/templos")
public class TemploController {

    private final TemploRepository temploRepository;
    private final DisponibilidadHorariaRepository horarioRepository;
    private final EventoCulturalRepository eventoRepository;
    private final AvisoNotificacionRepository avisoRepository;

    public TemploController(TemploRepository temploRepository,
                            DisponibilidadHorariaRepository horarioRepository,
                            EventoCulturalRepository eventoRepository,
                            AvisoNotificacionRepository avisoRepository) {
        this.temploRepository = temploRepository;
        this.horarioRepository = horarioRepository;
        this.eventoRepository = eventoRepository;
        this.avisoRepository = avisoRepository;
    }

    @GetMapping
    public List<Templo> listar() {
        return temploRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Templo> obtener(@PathVariable Integer id) {
        return ResponseEntity.of(temploRepository.findById(id));
    }

    @GetMapping("/{id}/horarios")
    public List<DisponibilidadHoraria> horarios(@PathVariable Integer id) {
        return horarioRepository.findByTemploId(id);
    }

    @GetMapping("/{id}/eventos")
    public List<EventoCultural> eventos(@PathVariable Integer id) {
        return eventoRepository.findByTemploIdOrderByFechaHora(id);
    }

    @GetMapping("/{id}/avisos")
    public List<AvisoNotificacion> avisos(@PathVariable Integer id) {
        return avisoRepository.findByTemploId(id);
    }
}
