INSERT INTO task (title, content, expiration_date, creation_date, priority, done_status)
VALUES
('Comprar pan', 'Comprar pan y leche en el supermercado', '2025-12-01', NOW(), 'MEDIUM', 'NOTDONE'),

('Estudiar Java', 'Repasar POO y arquitectura hexagonal', NULL, NOW(), 'HIGH', 'NOTDONE'),

('Hacer deporte', 'Ir al gimnasio 1 hora', '2025-11-30', NOW(), 'LOW', 'DONE'),

('Enviar email al profesor', 'Enviar avances del proyecto de agenda', '2025-12-15', NOW(), 'HIGH', 'NOTDONE');

('Pagar factura de luz', 'Revisar consumo y pagar antes del vencimiento', '2025-12-10', NOW(), 'MEDIUM', 'NOTDONE'),

('Actualizar CV', 'Añadir experiencia con Docker y MySQL', NULL, NOW(), 'HIGH', 'NOTDONE'),

('Comprar regalo', 'Comprar regalo de cumpleaños para un amigo', '2025-12-20', NOW(), 'LOW', 'DONE'),

('Revisar código del proyecto', 'Hacer refactor del TaskService y optimizar el repositorio', NULL, NOW(), 'HIGH', 'NOTDONE'),

('Planificar viaje', 'Buscar alojamiento y vuelos para vacaciones', '2026-01-05', NOW(), 'MEDIUM', 'NOTDONE');