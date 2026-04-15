package com.helloworld.restaurant.services.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RestauranteService {
    List<Restaurante> getAllRestaurantes();
    Optional<Restaurante> getRestauranteByCif(String cif);
    public List<Plato> getCartaFromRestaurante(String cif);

    boolean createRestaurante(Restaurante restaurante);
    boolean deleteRestaurante(String cif);
    Optional<Restaurante> modify(String cif, com.helloworld.restaurant.daos.model.Restaurante restaurante);
}
