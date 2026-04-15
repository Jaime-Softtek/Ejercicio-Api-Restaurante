package com.helloworld.restaurant.controllers.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import com.helloworld.restaurant.services.restaurante.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("restaurante/locales")
public class RestauranteControllerImpl implements RestauranteController{
    private final RestauranteService restauranteService;

    public RestauranteControllerImpl(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Override
    @GetMapping("")
    public List<Restaurante> getRestaurantes() {
        return restauranteService.getAllRestaurantes();
    }

    @Override
    @GetMapping("/{cif}")
    public Restaurante getRestauranteByCif(@PathVariable String cif) {
        var restaurante = restauranteService.getRestauranteByCif(cif);
        if (restaurante.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado");
        } else {
            return restaurante.get();
        }
    }

    @Override
    @GetMapping("/{cif}/carta")
    public List<Plato> getCartaFromRestaurante(@PathVariable String cif) {
        var carta = restauranteService.getCartaFromRestaurante(cif);
        if (carta.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado");
        } else {
            return carta;
        }
    }
}
