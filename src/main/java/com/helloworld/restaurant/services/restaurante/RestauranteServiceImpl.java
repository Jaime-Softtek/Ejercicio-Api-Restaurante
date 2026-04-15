package com.helloworld.restaurant.services.restaurante;

import com.helloworld.restaurant.daos.restaurante.RestauranteDao;
import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteServiceImpl implements RestauranteService{
    private final RestauranteDao restauranteDao;

    public RestauranteServiceImpl(RestauranteDao restauranteDao) {
        this.restauranteDao = restauranteDao;
    }

    @Override
    public List<Restaurante> getAllRestaurantes() {
        return restauranteDao.getRestaurantes()
                .stream()
                .map(Restaurante::fromRestauranteDao)
                .toList();
    }

    @Override
    public Optional<Restaurante> getRestauranteByCif(String cif) {
        return restauranteDao.getRestauranteByCif(cif).map(Restaurante::fromRestauranteDao);
    }

    @Override
    public List<Plato> getCartaFromRestaurante(String cif){
        var restaurante = getRestauranteByCif(cif);
        if (restaurante.isEmpty()){
            return List.of();
        } else {
            return restaurante.get().getCarta();
        }
    }

    @Override
    public void createRestaurante(Restaurante restaurante) {

        restauranteDao.saveRestaurante(Restaurante.fromRestauranteModel(restaurante));
    }
}
