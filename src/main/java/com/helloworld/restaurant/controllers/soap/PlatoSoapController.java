package com.helloworld.restaurant.controllers.soap;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.plato.PlatoSoapService;
import com.helloworld.restaurante.lab.GetPlatoRdnRes;
import com.helloworld.restaurante.lab.NotFoundFault_Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/plato-soap")
public class PlatoSoapController {
    private final PlatoSoapService platoSoapService;


    public PlatoSoapController(PlatoSoapService platoSoapService) {
        this.platoSoapService = platoSoapService;
    }

    @GetMapping("")
    public ResponseEntity<Plato> getPlatoRdnRes() throws NotFoundFault_Exception {
            return platoSoapService.getRandomPlatoFromSoap().map(ResponseEntity::ok).orElse(ResponseEntity.internalServerError().build());
    }
}
