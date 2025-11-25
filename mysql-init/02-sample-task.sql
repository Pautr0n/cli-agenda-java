INSERT INTO task (title, content, expiration_date, creation_date, priority, done_status)
VALUES
('Comprar pan', 'Comprar pan y leche en el supermercado', '2025-12-01', NOW(), 'MEDIUM', 'NOT_DONE'),

('Estudiar Java', 'Repasar POO y arquitectura hexagonal', NULL, NOW(), 'HIGH', 'NOT_DONE'),

('Hacer deporte', 'Ir al gimnasio 1 hora', '2025-11-30', NOW(), 'LOW', 'DONE'),

('Enviar email al profesor', 'Enviar avances del proyecto de agenda', '2025-12-15', NOW(), 'HIGH', 'NOT_DONE');