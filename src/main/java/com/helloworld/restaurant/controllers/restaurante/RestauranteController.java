package com.helloworld.restaurant.controllers.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;

import java.util.List;

public interface RestauranteController {
    List<Restaurante> getRestaurantes();
    Restaurante getRestauranteByCif(String cif);
    List<Plato> getCartaFromRestaurante(String cif);
}
