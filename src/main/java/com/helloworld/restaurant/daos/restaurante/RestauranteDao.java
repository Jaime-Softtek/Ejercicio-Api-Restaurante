package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.daos.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteDao {
    List<Restaurante> getRestaurantes();
    Optional<Restaurante> getRestauranteByCif(String cif);

    boolean saveRestaurante(Restaurante newRestaurante);
}
