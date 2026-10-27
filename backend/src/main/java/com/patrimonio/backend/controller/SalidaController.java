package com.patrimonio.backend.controller;

import com.patrimonio.backend.model.AsignacionGuia;
import com.patrimonio.backend.model.SalidaPedagogica;
import com.patrimonio.backend.repository.AsignacionGuiaRepository;
import com.patrimonio.backend.repository.SalidaPedagogicaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Salidas pedagógicas (colegio) y asignaciones de guía
@RestController
@RequestMapping("/api")
public class SalidaController {

    private final SalidaPedagogicaRepository salidaRepository;
    private final AsignacionGuiaRepository asignacionRepository;

    public SalidaController(SalidaPedagogicaRepository salidaRepository, AsignacionGuiaRepository asignacionRepository) {
        this.salidaRepository = salidaRepository;
        this.asignacionRepository = asignacionRepository;
    }

    // GET /api/salidas?colegioId=2
    @GetMapping("/salidas")
    public List<SalidaPedagogica> listarSalidas(@RequestParam(required = false) Integer colegioId) {
        if (colegioId == null) {
            return salidaRepository.findAll();
        }
        return salidaRepository.findByColegioId(colegioId);
    }

    @PostMapping("/salidas")
    public ResponseEntity<SalidaPedagogica> crearSalida(@RequestBody SalidaPedagogica salida) {
        if (salida.getCantidadEstudiantes() == null || salida.getCantidadEstudiantes() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        salida.setSalidaId(null);
        salida.setEstado("PENDIENTE");
        return ResponseEntity.status(HttpStatus.CREATED).body(salidaRepository.save(salida));
    }

    // GET /api/asignaciones?guiaId=3
    @GetMapping("/asignaciones")
    public List<AsignacionGuia> listarAsignaciones(@RequestParam(required = false) Integer guiaId) {
        if (guiaId == null) {
            return asignacionRepository.findAll();
        }
        return asignacionRepository.findByGuiaId(guiaId);
    }

    // PUT /api/asignaciones/1/responder?aceptar=true -> CONFIRMADA o RECHAZADA
    @PutMapping("/asignaciones/{id}/responder")
    public ResponseEntity<AsignacionGuia> responder(@PathVariable Integer id, @RequestParam boolean aceptar) {
        AsignacionGuia asignacion = asignacionRepository.findById(id).orElse(null);
        if (asignacion == null) {
            return ResponseEntity.notFound().build();
        }
        asignacion.setEstadoAsignacion(aceptar ? "CONFIRMADA" : "RECHAZADA");
        return ResponseEntity.ok(asignacionRepository.save(asignacion));
    }
}
