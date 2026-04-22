package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface PlatoController {
    public ResponseEntity<List<Plato>> getPlatos(@RequestParam String calorias, HttpServletRequest request) ;
	Plato getPlatosById(String id);
    ResponseEntity<List<Plato>> getPlatosByCalorias(String calorias);
	ResponseEntity<Plato> editPlato(Plato plato, int id);
	Optional<Plato> deletePlato(int id);
	ResponseEntity<Plato> createPlato(Plato plato);

}
