# Diagnóstico de equidad salarial entre mujeres y hombres

Proyecto Integrador de Aula 2026-2, Universidad Simón Bolívar.
Estructura de Datos (Prof. Omar Gutiérrez Acosta) y Base de Datos (Prof. Juan Carlos Calabria Sarmiento).
ODS 5: Igualdad de género.

## Paquetes

```
src/main/java/
  Modelo/       Empleado, Cargo, CambioSalario
  Estructura/   NodoDoble + ListaDobleEmpleados   (todos los empleados)
                NodoPila + PilaCambios             (historial de salarios, deshacer)
                NodoCola + ColaEmpleados           (casos por revisar)
  Logica/       GestorEmpleados, CalculadoraIndicadores, Indicador,
                GeneradorInforme, RevisionCasos, Formato
  Conexion/     EmpleadoDAO, CargoDAO (interfaces)
                EmpleadoDAOPrueba, CargoDAOPrueba (datos de prueba)
                ConexionBD (pendiente)
  Vista/        VentanaPrincipal y una clase por pantalla
  com/mycompany/brecha/Brecha.java   (main)
sql/
  01_ddl_oracle.sql, 02_dml_oracle.sql, 03_consultas_oracle.sql,
  04_empleados_adicionales.sql
```

Ninguna estructura usa colecciones de java.util.

## Ejecutar en NetBeans

Abrir la carpeta como proyecto Maven (File > Open Project) y ejecutar con Run (F6).
La clase principal es `com.mycompany.brecha.Brecha`.

## Base de datos

El programa se conecta a MySQL por JDBC (`ConexionBD`, `EmpleadoDAOMySQL`, `CargoDAOMySQL`).
Los datos de conexión están en `db.properties`. Los scripts de MySQL están en `sql/mysql`
y los de Oracle APEX en `sql`. Instrucciones en `INSTALAR_MYSQL.md`.

Si no logra conectarse, el programa avisa y usa los datos de prueba.
