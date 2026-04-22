package com.helloworld.restaurant.mapper.plato;

import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.mapper.Mapper;
import com.helloworld.restaurante.lab.GetPlatoRdnRes;
import org.springframework.stereotype.Component;

@Component
public class SoapToPlatoDao implements Mapper<GetPlatoRdnRes, Plato> {

    @Override
    public Plato map(GetPlatoRdnRes soapPlato) {
        int cat = switch (soapPlato.getCategoria()) {
            case "Entrante" -> 1;
            case "Plato principal" -> 2;
            case "Postre" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + soapPlato.getCategoria());
        };
        return new Plato(
                -1,
                soapPlato.getNombre(),
                soapPlato.getPrecio(),
                cat,
                soapPlato.getCalorias()
        );
    }
}
