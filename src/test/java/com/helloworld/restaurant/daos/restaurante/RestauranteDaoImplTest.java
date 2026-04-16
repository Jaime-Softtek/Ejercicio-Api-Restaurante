package com.helloworld.restaurant.daos.restaurante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class RestauranteDaoImplTest {

    @Autowired private RestauranteDao restauranteDao;

    @Test
    void ShouldReturnAllRestaurantes() throws Exception {
        var restaurante = restauranteDao.getRestaurantes();

        Assertions.assertEquals(3, restaurante.size());
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
