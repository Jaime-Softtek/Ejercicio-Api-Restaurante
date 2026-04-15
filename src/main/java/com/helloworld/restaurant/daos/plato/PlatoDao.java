package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Menu;
import com.helloworld.restaurant.daos.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoDao {
	List<Plato> getPlatos();
	Optional<Plato> getPlatosById(int id);
	List<Plato> getPlatosByCalorias(int calorias);
	List<Plato> getPlatosByCategoria(int categoria);
	Optional<Plato> updatePlato(int id, Plato plato);
	Optional<Plato> deletePlato(int id);
    List<Plato> getPlatosByRestaurant(String cif);
    Optional<Plato> cretePlato(Plato plato);

}
