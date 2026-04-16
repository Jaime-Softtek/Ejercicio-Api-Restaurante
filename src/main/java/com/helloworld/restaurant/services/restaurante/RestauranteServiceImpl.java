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
    public boolean createRestaurante(Restaurante restaurante) {
        if (restaurante == null) {
            return false;
        }
        if (restauranteDao.getRestauranteByCif(restaurante.getCif()).isPresent()) {
            return false;
        }

        return restauranteDao.saveRestaurante(Restaurante.fromRestauranteModel(restaurante));
    }

    @Override
    public boolean deleteRestaurante(String cif) {
        return restauranteDao.getRestauranteByCif(cif)
                .map(restaurante -> restauranteDao.eliminarRestaurante(cif))
                .orElse(false);
    }

    @Override
    public Optional<Restaurante> modifyRestaurante(String cif,
                                                   com.helloworld.restaurant.daos.model.Restaurante restaurante) {
        return restauranteDao.editRestaurante(cif, restaurante)
                .map(Restaurante::fromRestauranteDao);
    }
}
