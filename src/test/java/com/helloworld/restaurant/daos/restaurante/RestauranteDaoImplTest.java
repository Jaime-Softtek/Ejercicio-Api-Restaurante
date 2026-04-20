package com.helloworld.restaurant.daos.restaurante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RestauranteDaoImplTest {

    @Autowired
    private RestauranteDao restauranteDao;

    @Test
    @Sql(statements = {"INSERT INTO restaurante(cif, nombre, direccion, telefono) VALUES('A12345678', 'La Mar Salada', 'Calle Mayor, 1', '123456789');"})
    void ShouldReturnAllRestaurantes(){
        var restaurante = restauranteDao.getRestaurantes();

        Assertions.assertEquals(1, restaurante.size());
        Assertions.assertEquals("A12345678", restaurante.get(0).cif());
    }

    @Test
    @Sql(statements = {"INSERT INTO restaurante(cif, nombre, direccion, telefono) VALUES('A12345678', 'La Mar Salada', 'Calle Mayor, 1', '123456789');"})
    void ShouldReturnCifIfRestaurantExists() {
        var restaurante = restauranteDao.getRestauranteByCif("A12345678");

        Assertions.assertEquals("A12345678", restaurante.get().cif());
    }

    @Test
    void ShouldReturnEmptyObjectIfRestaurantDoesntExists() {
        var restaurante = restauranteDao.getRestauranteByCif("A000000000");

        Assertions.assertEquals(Optional.empty(), restaurante);
    }

    @Test
    @Sql(statements = {"INSERT INTO restaurante(cif, nombre, direccion, telefono) VALUES('A12345678', 'La Mar Salada', 'Calle Mayor, 1', '123456789');INSERT INTO plato(nombre, precio, categoria, calorias) VALUES('Ensalada', 6.00, 1, 150);INSERT INTO restaurante_plato(id_plato, cif_restaurante) VALUES(1, 'A12345678');"
    })
    void ShouldReturnACartaFromRestaurant() {
        var carta = restauranteDao.getRestauranteByCif("A12345678").get().carta();

        Assertions.assertFalse(carta.isEmpty());
    }
}
