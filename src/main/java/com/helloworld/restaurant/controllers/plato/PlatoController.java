package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;

import java.util.List;

public interface PlatoController {
	List<Plato> getPlatos();
	Plato getPlatosById(String id);
	List<Plato> getPlatosByCalorias(String calorias);

}
