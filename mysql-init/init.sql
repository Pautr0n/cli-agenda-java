CREATE DATABASE IF NOT EXISTS my_agend;
USE my_agend;

CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('nota', 'evento', 'tarea') NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    contenido TEXT,
    fecha_evento TIMESTAMP NULL,
    fecha_limite DATE NULL,
    completada BOOLEAN NULL,
    creada_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO items (tipo, titulo, contenido, fecha_evento, fecha_limite, completada)
VALUES
('nota', 'Comprar comida', 'Recordar comprar frutas, pan y leche en el supermercado.', NULL, '2024-02-01', FALSE),
('nota', 'Idea de proyecto', 'Desarrollar una app para tomar notas con recordatorios inteligentes.', NULL, NULL, FALSE),
('evento', 'Reunión de equipo', 'Revisión de avances del proyecto MyNotes.', '2024-02-10 10:00:00', NULL, NULL),
('evento', 'Cena familiar', 'Cena con la familia en casa de mis padres.', '2024-02-14 20:30:00', NULL, NULL),
('tarea', 'Enviar informe', 'Enviar informe mensual al departamento de gestión.', NULL, '2024-02-05', FALSE),
('tarea', 'Pagar alquiler', 'Pagar el alquiler del piso antes de la fecha límite.', NULL, '2024-02-03', FALSE),
('nota', 'Lista películas', 'Pendientes: Interstellar, El Padrino, Arrival.', NULL, NULL, FALSE),
('evento', 'Cumpleaños de Ana', 'Recordar comprar regalo para Ana.', '2024-03-01 18:00:00', NULL, NULL),
('tarea', 'Actualizar CV', 'Actualizar el CV con los últimos proyectos completados.', NULL, '2024-02-15', FALSE),
('nota', 'Receta pan casero', 'Harina, agua, sal, levadura. Dejar fermentar 2h.', NULL, NULL, FALSE);

