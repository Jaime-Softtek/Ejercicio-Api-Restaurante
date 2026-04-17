package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.daos.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteDao {
    List<Restaurante> getRestaurantes();
    Optional<Restaurante> getRestauranteByCif(String cif);

    Optional<Restaurante> saveRestaurante(Restaurante newRestaurante);
    boolean eliminarRestaurante(String cif);
    Optional<Restaurante> editRestaurante(String cif, Restaurante restaurante);
    boolean addPlatoToRestaurante(String cif, Integer idPlato);
    boolean removePlatoFromRestaurante(String cif, Integer idPlato);
}
