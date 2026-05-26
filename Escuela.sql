CREATE DATABASE tienda;

USE tienda;

CREATE TABLE productos (
    id INT PRIMARY KEY,
    nombre VARCHAR(50),
    precio DOUBLE,
    cantidad INT
);

select * from productos;