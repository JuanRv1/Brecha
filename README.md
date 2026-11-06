# Sistema Analizador de Brecha Salarial e Índice de Participación por Género en Organizaciones

Proyecto Integrador de Aula 2026-2. Universidad Simón Bolívar, Ingeniería de Datos e Inteligencia Artificial.
Cursos: Estructura de Datos (Prof. Omar Gutiérrez Acosta) y Base de Datos (Prof. Juan Carlos Calabria Sarmiento).

## 1. Datos del proyecto

**ODS:** 5, Igualdad de género (metas 5.1 y 5.5), relacionado con la meta 8.5 (igual remuneración por trabajo de igual valor).

| Integrante | Rol |
| --- | --- |
| Jesús Enrique Marín Pacheco | Coordinador e investigador |
| Juan David Romero Velásquez | Desarrollador de estructuras |
| Hemel Andrés Barrios Guerra | Desarrollador de aplicación |
| Luis Ángel Arango Verdooren | Desarrollador de base de datos |
| Angélica Marier López Jiménez | Documentadora |

## 2. Descripción

La Ley 1496 de 2011 obliga a las empresas colombianas a llevar un registro de cargos por sexo y remuneración y a justificar cualquier diferencia salarial entre mujeres y hombres, pero muchas organizaciones no tienen una herramienta para medirlo. Este prototipo administra la nómina de una organización y calcula la brecha salarial entre mujeres y hombres (general y por nivel jerárquico), el porcentaje de mujeres en cada nivel y los cargos donde personas de distinto sexo ganan diferente por el mismo trabajo. Con esos indicadores, que clasifica como adecuado, alerta o crítico, redacta un informe con hallazgos y recomendaciones. Los datos se guardan en MySQL y se organizan en memoria con estructuras de datos propias.

## 3. Instrucciones de ejecución

### Requisitos

| Elemento | Versión |
| --- | --- |
| Java (JDK) | 17 o superior |
| Gestor de base de datos | MySQL Community Server 8.4 (con MySQL Workbench para ejecutar los scripts) |
| Driver JDBC | MySQL Connector/J 8.4.0. No hay que descargarlo: está declarado en `pom.xml` y Maven lo baja solo la primera vez (se necesita internet) |
| IDE | Apache NetBeans (trae Maven incluido) |

### Pasos

1. **Instalar MySQL 8.4** y MySQL Workbench. Al instalar, anote la clave del usuario `root`. Hay una guía paso a paso para Windows en `INSTALAR_MYSQL.md`.
2. **Crear la base de datos.** En Workbench, conéctese a la instancia local y ejecute, en este orden:
   - `sql/mysql/01_ddl_mysql.sql`: crea la base `brecha_genero`, las 6 tablas, sus restricciones y una vista.
   - `sql/mysql/02_dml_mysql.sql`: carga 16 empleados de prueba de una organización ficticia.
   - `sql/mysql/03_consultas_mysql.sql` (opcional): consultas de indicadores para comprobar los datos.

   Para comprobarlo, `SELECT COUNT(*) FROM brecha_genero.empleado;` debe dar 16.
3. **Configurar la conexión.** Abra el archivo `db.properties`, que está en la raíz del proyecto, y cambie `ESCRIBA_AQUI_SU_CLAVE` por la clave de `root`. Si MySQL usa otro puerto o usuario, cámbielos en la misma línea:
   ```
   db.url=jdbc:mysql://localhost:3306/brecha_genero?serverTimezone=America/Bogota
   db.usuario=root
   db.clave=ESCRIBA_AQUI_SU_CLAVE
   ```
4. **Ejecutar.** En NetBeans: File → Open Project → seleccionar esta carpeta → Run (F6). La clase principal es `com.mycompany.brecha.Brecha`.
   Por consola, desde la raíz del proyecto: `mvn compile exec:java`
5. **Verificar.** La pantalla de inicio debe decir "Conectado a MySQL". Si dice "Datos de prueba (sin conexión)", el programa no encontró la base: revise que el servicio de MySQL esté encendido y que la clave de `db.properties` sea correcta.

## 4. Estructuras implementadas

Ninguna estructura usa colecciones de `java.util`.

| Estructura | Clases que la implementan | Entidad o tabla que gestiona | Uso en el programa |
| --- | --- | --- | --- |
| Lista doblemente enlazada | `Estructura/ListaDobleEmpleados`, `Estructura/NodoDoble` | Empleado (tablas `empleado`, `cargo`, `area`, `nivel_jerarquico` y salario vigente de `historial_salarial`) | Registrar, buscar, modificar salario, retirar, recorrer hacia adelante y hacia atrás, calcular indicadores |
| Pila | `Estructura/PilaCambios`, `Estructura/NodoPila` | CambioSalario (tabla `historial_salarial`) | Historial de cambios de salario de la sesión y "deshacer último cambio" |
| Cola | `Estructura/ColaEmpleados`, `Estructura/NodoCola` | Empleado (casos calculados, no se guardan) | Casos por revisar: personas que ganan 5 % o más por debajo del otro sexo en su mismo cargo, atendidas en orden de llegada |

Organización del código:

| Paquete | Contenido |
| --- | --- |
| `Modelo` | `Empleado`, `Cargo`, `CambioSalario` |
| `Estructura` | Nodos y estructuras propias |
| `Logica` | `GestorEmpleados` (une estructuras y base de datos), indicadores, informe y casos |
| `Conexion` | `ConexionBD` (JDBC), interfaces DAO y sus versiones para MySQL y de prueba |
| `Vista` | Interfaz gráfica en Swing |

## 5. Estado actual

**Funciona:**
- Carga de los empleados desde MySQL hacia la lista al iniciar el programa.
- Registrar, consultar, modificar salario, deshacer el último cambio y retirar. Cada cambio se guarda en MySQL con `PreparedStatement` y se conserva al reiniciar. Registrar y modificar usan transacciones (commit o rollback).
- Casos borde: estructura vacía, un solo elemento, código inexistente, código o documento repetido, salario negativo y sexo distinto de F o M.
- Nómina con recorrido hacia adelante y hacia atrás, consulta por rango salarial y por cargo, tablero de indicadores con gráficas, informe de análisis y cola de casos por revisar.
- Si no hay conexión con MySQL, el programa avisa y arranca con datos de prueba, que no se guardan.

**Limitaciones conocidas:**
- La pila solo guarda los cambios de salario hechos desde que se abrió el programa, así que "deshacer" no alcanza los cambios de sesiones anteriores. El historial completo sí queda en `historial_salarial`.
- La cola de casos se calcula al abrir el programa y no se guarda: los casos marcados como revisados vuelven a aparecer al reiniciar si la diferencia salarial sigue existiendo.
- La búsqueda por código y la consulta por rango recorren la lista completa (O(n)). Con nóminas de miles de empleados convendría un índice (árbol o tabla hash).
- Solo se pueden registrar personas en los 13 cargos que ya existen en la base de datos.

La carpeta `sql` (fuera de `sql/mysql`) conserva los scripts para Oracle APEX que se presentaron en el informe de avance. El programa no los usa.
