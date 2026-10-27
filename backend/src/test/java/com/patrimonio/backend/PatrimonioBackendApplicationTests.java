package com.patrimonio.backend;

import com.patrimonio.backend.controller.UsuarioController;
import com.patrimonio.backend.repository.TemploRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PatrimonioBackendApplicationTests {

    @Autowired
    private TemploRepository temploRepository;

    @Autowired
    private UsuarioController usuarioController;

    // La app arranca y data.sql carga los 3 templos
    @Test
    void cargaLosDatosIniciales() {
        assertEquals(3, temploRepository.count());
    }

    @Test
    void loginCorrectoEIncorrecto() {
        var correcto = usuarioController.login(Map.of("email", "MARIA@correo.cl ", "password", "maria123"));
        assertEquals(HttpStatus.OK, correcto.getStatusCode());

        var incorrecto = usuarioController.login(Map.of("email", "maria@correo.cl", "password", "mala"));
        assertEquals(HttpStatus.UNAUTHORIZED, incorrecto.getStatusCode());
    }
}
