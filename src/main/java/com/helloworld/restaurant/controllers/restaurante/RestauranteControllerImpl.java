package com.helloworld.restaurant.controllers.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import com.helloworld.restaurant.services.restaurante.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurante/locales")
public class RestauranteControllerImpl implements RestauranteController{
    private final RestauranteService restauranteService;

    public RestauranteControllerImpl(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Override
    @GetMapping("")
    @Operation(
            summary = "Get Restaurants",
            description = "Listar todos los restaurantes de la cadena")
    public List<Restaurante> getRestaurantes() {
        return restauranteService.getAllRestaurantes();
    }

    @Override
    @GetMapping("/{cif}")
    @Operation(summary = "Get Restaurant by CIF",
            description = "Obtener un restaurante específico mediante su CIF",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Restaurant",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Restaurante.class))),
                    @ApiResponse(responseCode = "400", description = "No se encuentra el Restaurante")
            }
    )
    public Restaurante getRestauranteByCif(@Parameter(description = "CIF del restaurante a buscar", required = true) @PathVariable String cif) {
        var restaurante = restauranteService.getRestauranteByCif(cif);
        if (restaurante.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado");
        } else {
            return restaurante.get();
        }
    }

    @Override
    @GetMapping("/{cif}/carta")
    @Operation(summary = "Get Carta By Restaurant",
            description = "Obtener la carta de un restaurante específico mediante su carta",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Restaurant",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Plato.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "No se encuentra el Restaurante")
            }
    )
    public List<Plato> getCartaFromRestaurante(@Parameter(description = "CIF del restaurante cuya carta mostrar", required = true) @PathVariable String cif) {
        var carta = restauranteService.getCartaFromRestaurante(cif);
        if (carta.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado");
        } else {
            return carta;
        }
    }

    //devolver en cabecera direccion de restaurante creado (/crear/idNewRestaurante)
    @PutMapping("/{cif}")
    public ResponseEntity<Restaurante> crearOEditarRestaurante( @PathVariable String cif, @RequestBody Restaurante restaurante) {
        if (restauranteService.getRestauranteByCif(cif).isEmpty()) {
            Optional<Restaurante> creado = restauranteService.createRestaurante(restaurante);
            if (creado.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            URI location = URI.create("restaurante/locales/" + creado.get().getCif());

            return ResponseEntity
                    .created(location)
                    .body(creado.get());
        } else {
            Optional<Restaurante> modificado = restauranteService.modifyRestaurante(cif, restaurante);

            if (modificado.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            URI location = URI.create("restaurante/locales/" + modificado.get().getCif());

            return ResponseEntity
                    .ok()               // 200 OK
                    .location(location) // header Location opcional en updates
                    .body(modificado.get());
        }
    }


    @DeleteMapping("/{cif}")
    public ResponseEntity<Restaurante> eliminarRestaurante(@PathVariable String cif) {
        boolean eliminado = restauranteService.deleteRestaurante(cif);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        URI location = URI.create("/restaurante");
        return ResponseEntity.noContent()
                .location(location)
                .build();
    }

    @PutMapping("/modificar/{cif}")
    public ResponseEntity<Restaurante> editarRestaurante(
            @PathVariable String cif,
            @RequestBody Restaurante restaurante) {

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