package com.helloworld.restaurant.controllers.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RestauranteController {
    List<Restaurante> getRestaurantes();
    Restaurante getRestauranteByCif(String cif);
    List<Plato> getCartaFromRestaurante(String cif);
    ResponseEntity<Restaurante> crearOEditarRestaurante(@PathVariable String cif, @RequestBody Restaurante restaurante);
    public ResponseEntity<Restaurante> eliminarRestaurante(@PathVariable String cif);
}
