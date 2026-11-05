-- =====================================================================
-- Datos de prueba SIMULADOS (organizacion ficticia) para MySQL.
-- Salarios anclados al salario minimo de 2026 ($1.750.905).
-- Ejecutar despues de 01_ddl_mysql.sql.
-- =====================================================================
USE brecha_genero;

INSERT INTO organizacion (id_organizacion, nit, razon_social, ciudad, sector)
VALUES (1, '900000001-1', 'Organizacion Caribe S.A.S. (ficticia)', 'Barranquilla', 'Servicios');

INSERT INTO area (id_area, nombre, id_organizacion) VALUES
(1, 'Administracion', 1), (2, 'Logistica', 1), (3, 'Produccion', 1), (4, 'Tecnologia', 1),
(5, 'Finanzas', 1), (6, 'Talento Humano', 1), (7, 'Gerencia', 1), (8, 'Comercial', 1);

INSERT INTO nivel_jerarquico (id_nivel, nombre, orden) VALUES
(1, 'Operativo', 1), (2, 'Profesional/Tecnico', 2), (3, 'Mando medio', 3), (4, 'Alta direccion', 4);

INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES
(1,  'Auxiliar administrativo',       1, 1),
(2,  'Auxiliar de bodega',            2, 1),
(3,  'Recepcionista',                 1, 1),
(4,  'Operario de produccion',        3, 1),
(5,  'Analista de datos',             4, 2),
(6,  'Contador',                      5, 2),
(7,  'Ingeniero de procesos',         3, 2),
(8,  'Coordinador de talento humano', 6, 3),
(9,  'Jefe de produccion',            3, 3),
(10, 'Jefe de tecnologia',            4, 3),
(11, 'Director financiero',           5, 4),
(12, 'Gerente general',               7, 4),
(13, 'Director comercial',            8, 4);

INSERT INTO empleado (codigo_empleado, documento, nombres, apellidos, sexo, fecha_ingreso, id_cargo) VALUES
('E013', '1001001013', 'Fernando', 'Ortiz',   'M', '2015-01-05', 12),
('E014', '1001001014', 'Javier',   'Pineda',  'M', '2016-03-07', 13),
('E012', '1001001012', 'Claudia',  'Mejia',   'F', '2017-06-01', 11),
('E010', '1001001010', 'Diego',    'Rojas',   'M', '2018-02-12', 9),
('E009', '1001001009', 'Paola',    'Vargas',  'F', '2019-08-01', 8),
('E011', '1001001011', 'Ricardo',  'Silva',   'M', '2019-11-25', 10),
('E007', '1001001007', 'Sofia',    'Herrera', 'F', '2020-10-05', 6),
('E008', '1001001008', 'Andres',   'Molina',  'M', '2021-04-19', 7),
('E004', '1001001004', 'Luis',     'Torres',  'M', '2021-09-01', 4),
('E006', '1001001006', 'Jorge',    'Castro',  'M', '2022-03-14', 5),
('E002', '1001001002', 'Carlos',   'Diaz',    'M', '2022-05-16', 2),
('E005', '1001001005', 'Laura',    'Ruiz',    'F', '2022-07-11', 5),
('E001', '1001001001', 'Ana',      'Perez',   'F', '2023-02-01', 1),
('E016', '1001001016', 'Camilo',   'Reyes',   'M', '2023-08-22', 6),
('E003', '1001001003', 'Maria',    'Gomez',   'F', '2024-01-15', 3),
('E015', '1001001015', 'Pedro',    'Gil',     'M', '2024-03-04', 1);

-- E001 tiene un registro cerrado de 2025 para mostrar el historial
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES
('E001', 1423500,  '2025-01-01 00:00:00', '2025-12-31 23:59:59'),
('E001', 1750905,  '2026-01-01 00:00:00', NULL),
('E002', 1850000,  '2026-01-01 00:00:00', NULL),
('E003', 1750905,  '2026-01-01 00:00:00', NULL),
('E004', 1900000,  '2026-01-01 00:00:00', NULL),
('E005', 4200000,  '2026-01-01 00:00:00', NULL),
('E006', 4600000,  '2026-01-01 00:00:00', NULL),
('E007', 4500000,  '2026-01-01 00:00:00', NULL),
('E008', 4800000,  '2026-01-01 00:00:00', NULL),
('E009', 7000000,  '2026-01-01 00:00:00', NULL),
('E010', 8200000,  '2026-01-01 00:00:00', NULL),
('E011', 8800000,  '2026-01-01 00:00:00', NULL),
('E012', 15000000, '2026-01-01 00:00:00', NULL),
('E013', 22761765, '2026-01-01 00:00:00', NULL),
('E014', 16500000, '2026-01-01 00:00:00', NULL),
('E015', 1900000,  '2026-01-01 00:00:00', NULL),
('E016', 4900000,  '2026-01-01 00:00:00', NULL);
