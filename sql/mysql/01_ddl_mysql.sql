-- =====================================================================
-- Diagnostico de equidad salarial - Script DDL para MySQL 8.4
-- Ejecutar en MySQL Workbench (boton del rayo).
-- =====================================================================

DROP DATABASE IF EXISTS brecha_genero;
CREATE DATABASE brecha_genero CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE brecha_genero;

CREATE TABLE organizacion (
    id_organizacion INT AUTO_INCREMENT PRIMARY KEY,
    nit             VARCHAR(15)  NOT NULL UNIQUE,
    razon_social    VARCHAR(120) NOT NULL,
    ciudad          VARCHAR(60)  NOT NULL,
    sector          VARCHAR(60)  NOT NULL
);

CREATE TABLE area (
    id_area         INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(60) NOT NULL,
    id_organizacion INT NOT NULL,
    CONSTRAINT uq_area_org UNIQUE (nombre, id_organizacion),
    CONSTRAINT fk_area_org FOREIGN KEY (id_organizacion)
        REFERENCES organizacion (id_organizacion)
);

CREATE TABLE nivel_jerarquico (
    id_nivel INT PRIMARY KEY,
    nombre   VARCHAR(40) NOT NULL UNIQUE,
    orden    INT         NOT NULL UNIQUE,
    CONSTRAINT ck_nivel_orden CHECK (orden BETWEEN 1 AND 4)
);

CREATE TABLE cargo (
    id_cargo INT AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(80) NOT NULL,
    id_area  INT NOT NULL,
    id_nivel INT NOT NULL,
    CONSTRAINT uq_cargo_area  UNIQUE (nombre, id_area),
    CONSTRAINT fk_cargo_area  FOREIGN KEY (id_area)  REFERENCES area (id_area),
    CONSTRAINT fk_cargo_nivel FOREIGN KEY (id_nivel) REFERENCES nivel_jerarquico (id_nivel)
);

CREATE TABLE empleado (
    codigo_empleado VARCHAR(10) PRIMARY KEY,
    documento       VARCHAR(15) NOT NULL UNIQUE,
    nombres         VARCHAR(60) NOT NULL,
    apellidos       VARCHAR(60) NOT NULL,
    sexo            CHAR(1)     NOT NULL,
    fecha_ingreso   DATE        NOT NULL,
    id_cargo        INT         NOT NULL,
    CONSTRAINT ck_empleado_sexo  CHECK (sexo IN ('F', 'M')),
    CONSTRAINT fk_empleado_cargo FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
);

-- Cada cambio de salario es una fila nueva. El salario vigente es el que
-- tiene fecha_fin en NULL. Se usa DATETIME para poder registrar varios
-- cambios el mismo dia (por ejemplo, un cambio y luego "deshacer").
CREATE TABLE historial_salarial (
    id_historial    INT AUTO_INCREMENT PRIMARY KEY,
    codigo_empleado VARCHAR(10)   NOT NULL,
    salario         DECIMAL(14,2) NOT NULL,
    fecha_inicio    DATETIME      NOT NULL,
    fecha_fin       DATETIME      NULL,
    CONSTRAINT ck_salario_positivo CHECK (salario > 0),
    CONSTRAINT ck_fechas CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio),
    CONSTRAINT fk_hist_empleado FOREIGN KEY (codigo_empleado)
        REFERENCES empleado (codigo_empleado) ON DELETE CASCADE
);

-- Vista con el salario vigente de cada empleado
CREATE VIEW v_salario_vigente AS
SELECT e.codigo_empleado,
       CONCAT(e.nombres, ' ', e.apellidos) AS nombre,
       e.sexo,
       c.nombre AS cargo,
       a.nombre AS area,
       n.orden  AS nivel,
       h.salario
FROM empleado e
INNER JOIN cargo c              ON c.id_cargo = e.id_cargo
INNER JOIN area a               ON a.id_area = c.id_area
INNER JOIN nivel_jerarquico n   ON n.id_nivel = c.id_nivel
INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado
WHERE h.fecha_fin IS NULL;
