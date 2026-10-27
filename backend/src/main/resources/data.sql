-- Mismos datos de ejemplo que la app (DatosEjemplo.kt). Los id se generan solos en este orden (1, 2, 3...).

INSERT INTO usuario (nombre, email, password, rol, institucion) VALUES
('María Pérez', 'maria@correo.cl', 'maria123', 'TURISTA', ''),
('Colegio San Ignacio', 'colegio@correo.cl', 'colegio123', 'COLEGIO', 'Colegio San Ignacio'),
('Carlos M.', 'carlos@correo.cl', 'carlos123', 'GUIA', ''),
('Encargado Catedral', 'encargado@correo.cl', 'encargado123', 'ENCARGADO', '');

INSERT INTO templo (encargado_id, nombre_oficial, direccion, latitud, longitud, descripcion_historica) VALUES
(4, 'Catedral de Santiago', 'Plaza de Armas s/n, Santiago Centro', -33.4378, -70.6504, 'Principal templo de la arquidiócesis, testigo de más de dos siglos de historia.'),
(4, 'Basílica del Carmen', 'Maipú', -33.5100, -70.7600, 'Templo votivo levantado tras la independencia.'),
(4, 'Iglesia San Francisco', 'Alameda, Santiago', -33.4450, -70.6460, 'Uno de los edificios más antiguos de la ciudad.');

INSERT INTO disponibilidad_horaria (templo_id, dia_semana, hora_apertura, hora_cierre, es_excepcional, estado) VALUES
(1, 'LUNES', '09:00', '19:00', FALSE, 'ABIERTO'),
(1, 'MARTES', '09:00', '19:00', FALSE, 'ABIERTO'),
(1, 'MIERCOLES', '09:00', '19:00', FALSE, 'ABIERTO'),
(1, 'JUEVES', '09:00', '19:00', FALSE, 'ABIERTO'),
(1, 'VIERNES', '09:00', '19:00', FALSE, 'ABIERTO'),
(1, 'SABADO', '10:00', '20:00', FALSE, 'EN_CELEBRACION'),
(1, 'DOMINGO', '-', '-', FALSE, 'CERRADO'),
(1, 'DOMINGO', '11:00', '13:00', TRUE, 'ABIERTO'),
(2, 'LUNES', '08:00', '18:00', FALSE, 'ABIERTO'),
(2, 'MARTES', '08:00', '18:00', FALSE, 'ABIERTO'),
(2, 'MIERCOLES', '08:00', '18:00', FALSE, 'ABIERTO'),
(2, 'JUEVES', '08:00', '18:00', FALSE, 'ABIERTO'),
(2, 'VIERNES', '08:00', '18:00', FALSE, 'ABIERTO'),
(2, 'SABADO', '08:00', '18:00', FALSE, 'ABIERTO'),
(2, 'DOMINGO', '08:00', '14:00', FALSE, 'EN_CELEBRACION'),
(3, 'LUNES', '-', '-', FALSE, 'CERRADO'),
(3, 'MARTES', '10:00', '18:00', FALSE, 'ABIERTO'),
(3, 'MIERCOLES', '10:00', '18:00', FALSE, 'ABIERTO'),
(3, 'JUEVES', '10:00', '18:00', FALSE, 'ABIERTO'),
(3, 'VIERNES', '10:00', '18:00', FALSE, 'ABIERTO'),
(3, 'SABADO', '10:00', '18:00', FALSE, 'ABIERTO'),
(3, 'DOMINGO', '10:00', '18:00', FALSE, 'ABIERTO');

INSERT INTO evento_cultural (templo_id, nombre_evento, tipo_evento, fecha_hora, descripcion) VALUES
(1, 'Misa solemne patronal', 'LITURGICO', '2026-10-25T18:00', 'Celebración patronal en la Catedral.'),
(3, 'Concierto de música sacra', 'CONCIERTO', '2026-10-28T20:00', 'Coro y órgano en San Francisco.'),
(2, 'Exposición de arte colonial', 'EXPOSICION', '2026-11-02T10:00', 'Muestra de pintura colonial.');

INSERT INTO aviso_notificacion (templo_id, tipo_aviso, mensaje, fecha_hora, sincronizado) VALUES
(1, 'GENERAL', 'Apertura especial este domingo de 11:00 a 13:00.', '2026-10-25T09:00', TRUE),
(2, 'GENERAL', 'Misa dominical a las 12:00, se pide silencio durante la celebración.', '2026-10-24T18:00', TRUE),
(3, 'GENERAL', 'El templo permanece cerrado los lunes.', '2026-10-23T12:00', TRUE);

INSERT INTO ruta_patrimonial (usuario_creador_id, nombre_ruta, tiempo_estimado_total, estado) VALUES
(2, 'Ruta Centro Histórico', 38.0, 'CONFIRMADA'),
(1, 'Ruta Basílicas del Sur', 38.0, 'CONFIRMADA');

INSERT INTO parada_ruta (ruta_id, templo_id, orden, tiempo_estimado_traslado) VALUES
(1, 1, 1, 0.0),
(1, 3, 2, 12.0),
(2, 1, 1, 0.0),
(2, 2, 2, 12.0);

INSERT INTO salida_pedagogica (colegio_id, ruta_id, fecha_salida, cantidad_estudiantes, observaciones, estado) VALUES
(2, 1, '2026-11-12', 32, 'Solicitamos guía con enfoque histórico.', 'PENDIENTE');

INSERT INTO asignacion_guia (ruta_id, guia_id, fecha_asignacion, estado_asignacion) VALUES
(1, 3, '2026-11-12T10:00', 'PENDIENTE'),
(2, 3, '2026-11-21T11:00', 'CONFIRMADA');
