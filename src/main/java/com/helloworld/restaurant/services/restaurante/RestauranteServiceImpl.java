package com.helloworld.restaurant.services.restaurante;

import com.helloworld.restaurant.daos.restaurante.RestauranteDao;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteServiceImpl implements RestauranteService{
    private final RestauranteDao restauranteDao;
    private final PlatoDao platoDao;

    public RestauranteServiceImpl(RestauranteDao restauranteDao, PlatoDao platoDao) {
        this.restauranteDao = restauranteDao;
        this.platoDao = platoDao;
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
    public Optional<Restaurante> createRestaurante(Restaurante restaurante) {
        return restauranteDao.saveRestaurante(Restaurante.fromRestauranteModel(restaurante)).map(Restaurante::fromRestauranteDao);
    }

    @Override
    public boolean deleteRestaurante(String cif) {
        return restauranteDao.getRestauranteByCif(cif)
                .map(restaurante -> restauranteDao.eliminarRestaurante(cif))
                .orElse(false);
    }

    @Override
    public Optional<Restaurante> modifyRestaurante(String cif, Restaurante restaurante) {
        return restauranteDao.editRestaurante(cif, Restaurante.fromRestauranteModel(restaurante))
                .map(Restaurante::fromRestauranteDao);
    }

    @Override
    public boolean addPlatoToRestaurante(String cif, Integer idPlato) {
        var restaurante = restauranteDao.getRestauranteByCif(cif);
        if (restaurante.isEmpty()) {
            return false;
        }

        var plato = platoDao.getPlatosById(idPlato);
        if (plato.isEmpty()) {
            return false;
        }

        var restModel = restaurante.get();
        var carta = restModel.carta();
        boolean platoYaExiste = carta.stream()
                .anyMatch(p -> p.id().equals(idPlato));

        if (platoYaExiste) {
            return false;
        }

        return restauranteDao.addPlatoToRestaurante(cif, idPlato);
    }

    @Override
    public boolean removePlatoFromRestaurante(String cif, Integer idPlato) {
        var restaurante = restauranteDao.getRestauranteByCif(cif);
        if (restaurante.isEmpty()) {
            return false;
        }

        var plato = platoDao.getPlatosById(idPlato);
        if (plato.isEmpty()) {
            return false;
        }

        var restModel = restaurante.get();
        var carta = restModel.carta();
        boolean platoExisteEnCarta = carta.stream()
                .anyMatch(p -> p.id().equals(idPlato));

        if (!platoExisteEnCarta) {
            return false;
        }

        return restauranteDao.removePlatoFromRestaurante(cif, idPlato);
    }
}
