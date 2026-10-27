package com.patrimonio.backend.controller;

import com.patrimonio.backend.model.ParadaRuta;
import com.patrimonio.backend.model.RutaPatrimonial;
import com.patrimonio.backend.repository.ParadaRutaRepository;
import com.patrimonio.backend.repository.RutaPatrimonialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    // Mismos tiempos que la app (Constantes.MINUTOS_VISITA y MINUTOS_TRASLADO)
    private static final int MINUTOS_VISITA = 13;
    private static final int MINUTOS_TRASLADO = 12;

    private final RutaPatrimonialRepository rutaRepository;
    private final ParadaRutaRepository paradaRepository;

    public RutaController(RutaPatrimonialRepository rutaRepository, ParadaRutaRepository paradaRepository) {
        this.rutaRepository = rutaRepository;
        this.paradaRepository = paradaRepository;
    }

    // GET /api/rutas?usuarioId=1 -> rutas de ese usuario; sin parámetro -> todas
    @GetMapping
    public List<RutaPatrimonial> listar(@RequestParam(required = false) Integer usuarioId) {
        if (usuarioId == null) {
            return rutaRepository.findAll();
        }
        return rutaRepository.findByUsuarioCreadorId(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaPatrimonial> obtener(@PathVariable Integer id) {
        return ResponseEntity.of(rutaRepository.findById(id));
    }

    // Una ruta nueva siempre nace en BORRADOR
    @PostMapping
    public ResponseEntity<RutaPatrimonial> crear(@RequestBody RutaPatrimonial ruta) {
        ruta.setRutaId(null);
        ruta.setEstado("BORRADOR");
        ruta.setTiempoEstimadoTotal(0.0);
        return ResponseEntity.status(HttpStatus.CREATED).body(rutaRepository.save(ruta));
    }

    // Pasa a CONFIRMADA y calcula el tiempo total con sus paradas
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<RutaPatrimonial> confirmar(@PathVariable Integer id) {
        RutaPatrimonial ruta = rutaRepository.findById(id).orElse(null);
        if (ruta == null) {
            return ResponseEntity.notFound().build();
        }
        int cantidad = paradaRepository.findByRutaIdOrderByOrden(id).size();
        int minutos = cantidad == 0 ? 0 : cantidad * MINUTOS_VISITA + (cantidad - 1) * MINUTOS_TRASLADO;
        ruta.setEstado("CONFIRMADA");
        ruta.setTiempoEstimadoTotal((double) minutos);
        return ResponseEntity.ok(rutaRepository.save(ruta));
    }

    @GetMapping("/{id}/paradas")
    public List<ParadaRuta> paradas(@PathVariable Integer id) {
        return paradaRepository.findByRutaIdOrderByOrden(id);
    }

    // Recibe {"temploId": 3}; la parada queda al final de la ruta
    @PostMapping("/{id}/paradas")
    public ResponseEntity<ParadaRuta> agregarParada(@PathVariable Integer id, @RequestBody ParadaRuta parada) {
        if (!rutaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<ParadaRuta> actuales = paradaRepository.findByRutaIdOrderByOrden(id);
        parada.setParadaId(null);
        parada.setRutaId(id);
        parada.setOrden(actuales.size() + 1);
        parada.setTiempoEstimadoTraslado(actuales.isEmpty() ? 0.0 : MINUTOS_TRASLADO);
        return ResponseEntity.status(HttpStatus.CREATED).body(paradaRepository.save(parada));
    }

    @DeleteMapping("/paradas/{paradaId}")
    public ResponseEntity<Void> quitarParada(@PathVariable Integer paradaId) {
        if (!paradaRepository.existsById(paradaId)) {
            return ResponseEntity.notFound().build();
        }
        paradaRepository.deleteById(paradaId);
        return ResponseEntity.noContent().build();
    }
}
