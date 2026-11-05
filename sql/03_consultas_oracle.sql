-- =====================================================================
-- Consultas de indicadores (Oracle, con INNER JOIN)
-- =====================================================================

-- 1) Brecha salarial no ajustada por nivel jerarquico
SELECT n.nombre AS nivel,
       ROUND(AVG(CASE WHEN e.sexo = 'M' THEN h.salario END)) AS promedio_hombres,
       ROUND(AVG(CASE WHEN e.sexo = 'F' THEN h.salario END)) AS promedio_mujeres,
       ROUND(100 * (AVG(CASE WHEN e.sexo = 'M' THEN h.salario END)
                  - AVG(CASE WHEN e.sexo = 'F' THEN h.salario END))
                  / AVG(CASE WHEN e.sexo = 'M' THEN h.salario END), 2) AS brecha_pct
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN nivel_jerarquico n   ON n.id_nivel = c.id_nivel
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL
GROUP BY n.orden, n.nombre
ORDER BY n.orden;

-- 2) Porcentaje de mujeres por nivel jerarquico (indicador tipo ODS 5.5.2)
SELECT n.nombre AS nivel,
       COUNT(*) AS total,
       SUM(CASE WHEN e.sexo = 'F' THEN 1 ELSE 0 END) AS mujeres,
       ROUND(100 * SUM(CASE WHEN e.sexo = 'F' THEN 1 ELSE 0 END) / COUNT(*), 1) AS pct_mujeres
FROM empleado e
INNER JOIN cargo c            ON c.id_cargo = e.id_cargo
INNER JOIN nivel_jerarquico n ON n.id_nivel = c.id_nivel
GROUP BY n.orden, n.nombre
ORDER BY n.orden;

-- 3) Brecha por cargo (solo cargos ocupados por hombres y mujeres)
SELECT c.nombre AS cargo,
       ROUND(AVG(CASE WHEN e.sexo = 'M' THEN h.salario END)) AS promedio_hombres,
       ROUND(AVG(CASE WHEN e.sexo = 'F' THEN h.salario END)) AS promedio_mujeres
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL
GROUP BY c.nombre
HAVING COUNT(DISTINCT e.sexo) = 2;

-- 4) Empleados en un rango salarial (la misma consulta que resuelve el AVL)
SELECT e.codigo_empleado, e.nombres || ' ' || e.apellidos AS nombre,
       e.sexo, c.nombre AS cargo, h.salario
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL
  AND h.salario BETWEEN 4000000 AND 9000000
ORDER BY h.salario;

-- 5) Empleados por area con su nivel
SELECT a.nombre AS area, e.codigo_empleado, e.nombres, e.sexo, n.nombre AS nivel
FROM empleado e
INNER JOIN cargo c            ON c.id_cargo = e.id_cargo
INNER JOIN area a             ON a.id_area = c.id_area
INNER JOIN nivel_jerarquico n ON n.id_nivel = c.id_nivel
ORDER BY a.nombre, n.orden;
