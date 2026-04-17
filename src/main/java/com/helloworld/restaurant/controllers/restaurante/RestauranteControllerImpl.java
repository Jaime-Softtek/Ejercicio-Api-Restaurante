package com.helloworld.restaurant.controllers.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import com.helloworld.restaurant.services.restaurante.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("restaurante/locales")
public class RestauranteControllerImpl implements RestauranteController{
    private final RestauranteService restauranteService;

    public RestauranteControllerImpl(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Override
    @GetMapping("")
    @Operation(summary = "Listar todos los restaurantes de la cadena")
    public List<Restaurante> getRestaurantes() {
        return restauranteService.getAllRestaurantes();
    }

    @Override
    @GetMapping("/{cif}")
    @Operation(summary = "Obtener un restaurante específico mediante su CIF")
    public Restaurante getRestauranteByCif(@PathVariable String cif) {
        var restaurante = restauranteService.getRestauranteByCif(cif);
        if (restaurante.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado");
        } else {
            return restaurante.get();
        }
    }

    @Override
    @GetMapping("/{cif}/carta")
    @Operation(summary = "Obtener la carta de un restaurante específico mediante su carta")
    public List<Plato> getCartaFromRestaurante(@PathVariable String cif) {
        var carta = restauranteService.getCartaFromRestaurante(cif);
        if (carta.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado");
        } else {
            return carta;
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearRestaurante(@RequestBody Restaurante restaurante) {
        boolean creado = restauranteService.createRestaurante(restaurante);

        if (!creado) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Restaurante ya existe o datos inválidos");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Restaurante creado exitosamente");
    }

    @DeleteMapping("/eliminar/{cif}")
    public ResponseEntity<String> eliminarRestaurante(@PathVariable String cif) {
        boolean eliminado = restauranteService.deleteRestaurante(cif);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un restaurante con cif: " + cif);
        }

        return ResponseEntity.ok("Restaurante eliminado correctamente");
    }

    @PutMapping("/modificar/{cif}")
    public ResponseEntity<Restaurante> editarRestaurante(
            @PathVariable String cif,
            @RequestBody com.helloworld.restaurant.daos.model.Restaurante restaurante) {

        return restauranteService.modifyRestaurante(cif, restaurante)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{cif}/platos/{idPlato}")
    public ResponseEntity<String> addPlatoToRestaurante(
            @PathVariable String cif,
            @PathVariable Integer idPlato) {

        boolean added = restauranteService.addPlatoToRestaurante(cif, idPlato);

        if (!added) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo añadir el plato. Verifica que el plato no esté ya en la carta.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Plato añadido correctamente");
    }

    @DeleteMapping("/{cif}/platos/{idPlato}")
    public ResponseEntity<String> removePlatoFromRestaurante(
            @PathVariable String cif,
            @PathVariable Integer idPlato) {

        boolean removed = restauranteService.removePlatoFromRestaurante(cif, idPlato);

        if (!removed) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo eliminar. Verifica que el plato existe.");
        }

        return ResponseEntity.ok("Plato eliminado de la carta correctamente");
    }
}
