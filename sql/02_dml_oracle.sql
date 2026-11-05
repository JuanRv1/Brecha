-- =====================================================================
-- Datos de prueba SIMULADOS (organizacion ficticia).
-- Salarios anclados al SMLMV 2026 ($1.750.905).
-- =====================================================================

INSERT INTO organizacion (id_organizacion, nit, razon_social, ciudad, sector)
VALUES (1, '900000001-1', 'Organizacion Caribe S.A.S. (ficticia)', 'Barranquilla', 'Servicios');

INSERT INTO area (id_area, nombre, id_organizacion) VALUES (1, 'Administracion', 1);
INSERT INTO area (id_area, nombre, id_organizacion) VALUES (2, 'Logistica', 1);
INSERT INTO area (id_area, nombre, id_organizacion) VALUES (3, 'Produccion', 1);
INSERT INTO area (id_area, nombre, id_organizacion) VALUES (4, 'Tecnologia', 1);
INSERT INTO area (id_area, nombre, id_organizacion) VALUES (5, 'Finanzas', 1);
INSERT INTO area (id_area, nombre, id_organizacion) VALUES (6, 'Talento Humano', 1);
INSERT INTO area (id_area, nombre, id_organizacion) VALUES (7, 'Gerencia', 1);
INSERT INTO area (id_area, nombre, id_organizacion) VALUES (8, 'Comercial', 1);

INSERT INTO nivel_jerarquico VALUES (1, 'Operativo', 1);
INSERT INTO nivel_jerarquico VALUES (2, 'Profesional/Tecnico', 2);
INSERT INTO nivel_jerarquico VALUES (3, 'Mando medio', 3);
INSERT INTO nivel_jerarquico VALUES (4, 'Alta direccion', 4);

INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (1,  'Auxiliar administrativa', 1, 1);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (2,  'Auxiliar de bodega', 2, 1);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (3,  'Recepcionista', 1, 1);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (4,  'Operario de produccion', 3, 1);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (5,  'Analista de datos', 4, 2);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (6,  'Contador', 5, 2);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (7,  'Ingeniero de procesos', 3, 2);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (8,  'Coordinador de talento humano', 6, 3);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (9,  'Jefe de produccion', 3, 3);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (10, 'Jefe de tecnologia', 4, 3);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (11, 'Director financiero', 5, 4);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (12, 'Gerente general', 7, 4);
INSERT INTO cargo (id_cargo, nombre, id_area, id_nivel) VALUES (13, 'Director comercial', 8, 4);

INSERT INTO empleado VALUES ('E001','1001001001','Ana','Perez','F',DATE '2023-02-01',1);
INSERT INTO empleado VALUES ('E002','1001001002','Carlos','Diaz','M',DATE '2022-05-16',2);
INSERT INTO empleado VALUES ('E003','1001001003','Maria','Gomez','F',DATE '2024-01-15',3);
INSERT INTO empleado VALUES ('E004','1001001004','Luis','Torres','M',DATE '2021-09-01',4);
INSERT INTO empleado VALUES ('E005','1001001005','Laura','Ruiz','F',DATE '2022-07-11',5);
INSERT INTO empleado VALUES ('E006','1001001006','Jorge','Castro','M',DATE '2022-03-14',5);
INSERT INTO empleado VALUES ('E007','1001001007','Sofia','Herrera','F',DATE '2020-10-05',6);
INSERT INTO empleado VALUES ('E008','1001001008','Andres','Molina','M',DATE '2021-04-19',7);
INSERT INTO empleado VALUES ('E009','1001001009','Paola','Vargas','F',DATE '2019-08-01',8);
INSERT INTO empleado VALUES ('E010','1001001010','Diego','Rojas','M',DATE '2018-02-12',9);
INSERT INTO empleado VALUES ('E011','1001001011','Ricardo','Silva','M',DATE '2019-11-25',10);
INSERT INTO empleado VALUES ('E012','1001001012','Claudia','Mejia','F',DATE '2017-06-01',11);
INSERT INTO empleado VALUES ('E013','1001001013','Fernando','Ortiz','M',DATE '2015-01-05',12);
INSERT INTO empleado VALUES ('E014','1001001014','Javier','Pineda','M',DATE '2016-03-07',13);

-- Historial: E001 tiene un registro cerrado de 2025 para mostrar trazabilidad
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E001', 1423500,  DATE '2025-01-01', DATE '2025-12-31');
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E001', 1750905,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E002', 1850000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E003', 1750905,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E004', 1900000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E005', 4200000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E006', 4600000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E007', 4500000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E008', 4800000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E009', 7000000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E010', 8200000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E011', 8800000,  DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E012', 15000000, DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E013', 22761765, DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E014', 16500000, DATE '2026-01-01', NULL);

COMMIT;
