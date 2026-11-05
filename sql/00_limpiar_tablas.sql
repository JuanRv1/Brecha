-- Borra las tablas viejas del workspace y las del primer intento
-- para poder crear las del proyecto con sus nombres originales.
DROP TABLE organizacion CASCADE CONSTRAINTS;
DROP TABLE nivel_jerarquico CASCADE CONSTRAINTS;
DROP TABLE area CASCADE CONSTRAINTS;
DROP TABLE cargo CASCADE CONSTRAINTS;
DROP TABLE empleado CASCADE CONSTRAINTS;
