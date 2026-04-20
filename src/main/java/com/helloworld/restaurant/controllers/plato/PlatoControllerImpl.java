package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.menu.MenuService;
import com.helloworld.restaurant.services.plato.PlatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
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

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Plato> editPlato(@RequestBody Plato plato, @PathVariable int id) {

        Optional<Plato> platoNuevo = platoService.editPlato(id, plato);

        Optional<Plato> platoExistente = platoService.getPlatosById(id);

        if (platoNuevo.isEmpty() || platoExistente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato para actualizar no encontrado");
        } else {

            URI location = URI.create("restaurante/platos/"+ id);
            return ResponseEntity
                    .created(location)
                    .body(platoExistente.get());
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public Optional<Plato> deletePlato(@PathVariable int id) {
        Optional<Plato> platoBorrado = platoService.deletePlato(id);

        if (platoBorrado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato para borrar no encontrado");
        } else {
            return platoBorrado;
        }

    }

    @Override
    @PostMapping("")
    public ResponseEntity<Plato> createPlato(@RequestBody Plato plato) {
        Optional<Plato> platoCreado = platoService.createPlato(plato);

        Optional<Plato> platoCreadoCompleto = platoService.getPlatos().stream().filter(p -> p.getNombre().equals(plato.getNombre())).findFirst();

        if (platoCreado.isEmpty() || platoCreadoCompleto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato para crear no encontrado");
        } else {

            URI location = URI.create("restaurante/platos/"+ platoCreadoCompleto.get().getId());
            return ResponseEntity
                    .created(location)
                    .body(platoCreadoCompleto.get());

        }
    }

}
