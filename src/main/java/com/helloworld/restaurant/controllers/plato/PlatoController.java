package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoController {
	List<Plato> getPlatos();
	Plato getPlatosById(String id);
	List<Plato> getPlatosByCalorias(String calorias);
	Optional<Plato> editPlato(int id, String nombre, double precio, Plato.Categoria categoria, int calorias);
	Optional<Plato> deletePlato(int id);

}
