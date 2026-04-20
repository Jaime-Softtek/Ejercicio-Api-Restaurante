DROP TABLE IF EXISTS restaurante_plato;
DROP TABLE IF EXISTS restaurante;
DROP TABLE IF EXISTS plato;

CREATE TABLE plato (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL,
                       precio NUMERIC(6,2) NOT NULL,
                       categoria INT NOT NULL,
                       calorias INT NOT NULL
);

CREATE TABLE restaurante (
                             cif VARCHAR(255) PRIMARY KEY,
                             nombre VARCHAR(255) NOT NULL,
                             direccion VARCHAR(255) NOT NULL,
                             telefono VARCHAR(255) NOT NULL
);

CREATE TABLE restaurante_plato (
                                   id_plato INT,
                                   cif_restaurante VARCHAR(255),
                                   PRIMARY KEY (id_plato, cif_restaurante)
);