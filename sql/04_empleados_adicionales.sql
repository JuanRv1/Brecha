-- =====================================================================
-- Empleados adicionales para la demostracion de "Casos por revisar".
-- Se ejecuta DESPUES de 02_dml_oracle.sql (en Oracle APEX > SQL Scripts).
-- =====================================================================

-- El cargo 1 lo ocupan una mujer y un hombre, asi que se le da un nombre neutro
UPDATE cargo SET nombre = 'Auxiliar administrativo' WHERE id_cargo = 1;

-- Un hombre en el cargo 1 y otro en el cargo 6 (Contador)
INSERT INTO empleado VALUES ('E015','1001001015','Pedro','Gil','M',DATE '2024-03-04',1);
INSERT INTO empleado VALUES ('E016','1001001016','Camilo','Reyes','M',DATE '2023-08-22',6);

INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E015', 1900000, DATE '2026-01-01', NULL);
INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) VALUES ('E016', 4900000, DATE '2026-01-01', NULL);

COMMIT;

-- Consulta de comprobacion: cargos donde trabajan hombres y mujeres y sus promedios
SELECT c.nombre AS cargo,
       ROUND(AVG(CASE WHEN e.sexo = 'F' THEN h.salario END)) AS promedio_mujeres,
       ROUND(AVG(CASE WHEN e.sexo = 'M' THEN h.salario END)) AS promedio_hombres
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL
GROUP BY c.nombre
HAVING COUNT(DISTINCT e.sexo) = 2;
