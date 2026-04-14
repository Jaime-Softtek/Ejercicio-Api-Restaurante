package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.menu.MenuService;
import com.helloworld.restaurant.services.plato.PlatoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurante/platos")
public class PlatoControllerImpl implements PlatoController {

	private final PlatoService platoService;


	public PlatoControllerImpl(PlatoService platoService, MenuService menuService) {
		this.platoService = platoService;

    }


	@Override
	@GetMapping("")
	public List<Plato> getPlatos() {
		List<Plato> platos = platoService.getPlatos();
		return platos;
	}

	@Override
	@GetMapping("/{id}")
	public Plato getPlatosById(@PathVariable String id) {
		Optional<Plato> plato = platoService.getPlatosById(Integer.parseInt(id));
		if (plato.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
		} else {
			return plato.get();
		}
	}
	@Override
	@GetMapping("/calorias")
	public List<Plato> getPlatosByCalorias(@RequestParam String calorias) {

		List<Plato> platos = platoService.getPlatosByCalorias(Integer.parseInt(calorias));
		if (platos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Platos no encontrados");
		} else {
			return platos;
		}
	}



}
