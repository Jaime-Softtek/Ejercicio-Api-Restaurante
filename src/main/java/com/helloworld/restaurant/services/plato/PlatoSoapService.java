package com.helloworld.restaurant.services.plato;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurante.lab.NotFoundFault_Exception;

import java.util.Optional;

public interface PlatoSoapService {
    public Optional<Plato> getRandomPlatoFromSoap() throws NotFoundFault_Exception;
}
