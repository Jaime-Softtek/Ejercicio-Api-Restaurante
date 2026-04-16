package com.helloworld.restaurant.services.plato;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
	List<Plato> getPlatos();
	Optional<Plato> getPlatosById(int id);
	List<Plato> getPlatosByCalorias(int calorias);
	List<Plato> getPlatosByCategoria(int categoria);
	Optional<Plato> editPlato(int id, Plato plato);
	Optional<Plato> deletePlato(int id);
	Optional<Plato> createPlato(Plato plato);

}
