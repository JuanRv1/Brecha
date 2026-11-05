-- =====================================================================
-- Consultas de indicadores (MySQL, con INNER JOIN)
-- =====================================================================
USE brecha_genero;

-- 1) Brecha salarial por nivel jerarquico
SELECT n.nombre AS nivel,
       ROUND(AVG(CASE WHEN e.sexo = 'M' THEN h.salario END)) AS promedio_hombres,
       ROUND(AVG(CASE WHEN e.sexo = 'F' THEN h.salario END)) AS promedio_mujeres,
       ROUND(100 * (AVG(CASE WHEN e.sexo = 'M' THEN h.salario END)
                  - AVG(CASE WHEN e.sexo = 'F' THEN h.salario END))
                  / AVG(CASE WHEN e.sexo = 'M' THEN h.salario END), 1) AS brecha_pct
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN nivel_jerarquico n   ON n.id_nivel = c.id_nivel
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL
GROUP BY n.orden, n.nombre
ORDER BY n.orden;

-- 2) Porcentaje de mujeres por nivel
SELECT n.nombre AS nivel,
       COUNT(*) AS total,
       SUM(CASE WHEN e.sexo = 'F' THEN 1 ELSE 0 END) AS mujeres,
       ROUND(100 * SUM(CASE WHEN e.sexo = 'F' THEN 1 ELSE 0 END) / COUNT(*), 1) AS pct_mujeres
FROM empleado e
INNER JOIN cargo c            ON c.id_cargo = e.id_cargo
INNER JOIN nivel_jerarquico n ON n.id_nivel = c.id_nivel
GROUP BY n.orden, n.nombre
ORDER BY n.orden;

-- 3) Cargos donde trabajan hombres y mujeres, con sus promedios
SELECT c.nombre AS cargo,
       ROUND(AVG(CASE WHEN e.sexo = 'F' THEN h.salario END)) AS promedio_mujeres,
       ROUND(AVG(CASE WHEN e.sexo = 'M' THEN h.salario END)) AS promedio_hombres
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL
GROUP BY c.nombre
HAVING COUNT(DISTINCT e.sexo) = 2;

-- 4) Empleados en un rango salarial
SELECT e.codigo_empleado, CONCAT(e.nombres, ' ', e.apellidos) AS nombre,
       e.sexo, c.nombre AS cargo, h.salario
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL
  AND h.salario BETWEEN 4000000 AND 9000000
ORDER BY h.salario;

-- 5) Historial completo de salarios de un empleado
SELECT h.codigo_empleado, h.salario, h.fecha_inicio, h.fecha_fin
FROM historial_salarial h
INNER JOIN empleado e ON e.codigo_empleado = h.codigo_empleado
WHERE e.codigo_empleado = 'E001'
ORDER BY h.fecha_inicio;
