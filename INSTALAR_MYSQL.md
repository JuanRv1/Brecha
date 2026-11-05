# Cómo conectar el proyecto con MySQL (Windows)

1. Descargar MySQL Community Server 8.4 LTS en https://dev.mysql.com/downloads/mysql/
   (sistema: Microsoft Windows, archivo: MSI Installer). En la página de descarga
   elegir "No thanks, just start my download".
2. Instalar con la opción Typical. Al final se abre MySQL Configurator:
   tipo "Development Computer", puerto 3306, escribir una clave para el usuario root
   (anotarla) y dejar MySQL como servicio de Windows que arranca solo.
3. Descargar e instalar MySQL Workbench: https://dev.mysql.com/downloads/workbench/
4. Abrir Workbench, entrar a "Local instance MySQL" con la clave de root.
5. File > Open SQL Script > `sql/mysql/01_ddl_mysql.sql` y ejecutar con el rayo.
   Repetir con `02_dml_mysql.sql`. `03_consultas_mysql.sql` tiene las consultas de prueba.
6. Abrir el archivo `db.properties` (en la carpeta del proyecto) y cambiar
   `ESCRIBA_AQUI_SU_CLAVE` por la clave de root.
7. Abrir el proyecto en NetBeans (File > Open Project). La primera vez NetBeans
   descarga el conector de MySQL, así que debe haber internet.
8. Run. En la pantalla de inicio debe decir "Conectado a MySQL".

Para comprobar que guarda: registrar un empleado, cerrar el programa, volver a abrirlo
y buscarlo. También se ve en Workbench con `SELECT * FROM empleado;`.
