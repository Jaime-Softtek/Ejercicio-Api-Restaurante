package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PlatoController {
	List<Plato> getPlatos();
	Plato getPlatosById(String id);
	List<Plato> getPlatosByCalorias(String calorias);
	ResponseEntity<Plato> editPlato(Plato plato, int id);
	Optional<Plato> deletePlato(int id);
	ResponseEntity<Plato> createPlato(Plato plato);

}
