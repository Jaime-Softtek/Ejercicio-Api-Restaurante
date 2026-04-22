package com.helloworld.restaurant.mapper.plato;

import com.helloworld.restaurant.mapper.Mapper;
import com.helloworld.restaurant.model.Plato;
import org.springframework.stereotype.Component;

@Component
public class PlatoToDao implements Mapper<Plato, com.helloworld.restaurant.daos.model.Plato> {
    @Override
    public com.helloworld.restaurant.daos.model.Plato map(Plato plato) {
        int cat = switch (plato.getCategoria()) {
            case PRIMER_PLATO -> 1;
            case SEGUNDO_PLATO -> 2;
            case POSTRE -> 3;
        };

        return new com.helloworld.restaurant.daos.model.Plato(
                plato.getId(),
                plato.getNombre(),
                plato.getPrecio(),
                cat,
                plato.getCalorias()
        );
    }
}
