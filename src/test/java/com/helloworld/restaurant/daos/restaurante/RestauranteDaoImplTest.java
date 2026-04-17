package com.helloworld.restaurant.daos.restaurante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/schema.sql")
class RestauranteDaoImplTest {

    @Autowired private RestauranteDao restauranteDao;

    @Test
    @Sql(statements = {"INSERT INTO restaurante(cif, nombre, direccion, telefono) VALUES('A12345678', 'La Mar Salada', 'Calle Mayor, 1', '123456789');"})
    void ShouldReturnAllRestaurantes() throws Exception {
        var restaurante = restauranteDao.getRestaurantes();

        Assertions.assertEquals(1, restaurante.size());
        Assertions.assertEquals("A12345678", restaurante.get(0).cif());
    }

    @Test
    void ShouldReturnCifIfRestaurantExists() {
        var restaurante = restauranteDao.getRestauranteByCif("A12345678");

        Assertions.assertEquals("A12345678", restaurante.get().cif());
    }

    @Test
    void ShouldReturnEmptyObjectIfRestaurantDoesntExists(){
        var restaurante = restauranteDao.getRestauranteByCif("A000000000");

        Assertions.assertEquals(Optional.empty(), restaurante);
    }

    @Test
    void ShouldReturnACartaFromRestaurant() {
        var carta = restauranteDao.getRestauranteByCif("A12345678").get().carta();

        Assertions.assertFalse(carta.isEmpty());
    }
}
