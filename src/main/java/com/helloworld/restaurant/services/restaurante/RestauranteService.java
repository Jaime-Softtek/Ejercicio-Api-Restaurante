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

    Optional<Restaurante> createRestaurante(Restaurante restaurante);
    boolean deleteRestaurante(String cif);
    Optional<Restaurante> modifyRestaurante(String cif, Restaurante restaurante);
}
