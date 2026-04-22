package com.helloworld.restaurant.mapper.plato;

import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class DaoToPlato implements Mapper<Plato, com.helloworld.restaurant.model.Plato> {
    @Override
    public com.helloworld.restaurant.model.Plato map(Plato plato) {
        com.helloworld.restaurant.model.Plato.Categoria categoria = switch (plato.categoria()) {
            case 2 -> com.helloworld.restaurant.model.Plato.Categoria.SEGUNDO_PLATO;
            case 3 -> com.helloworld.restaurant.model.Plato.Categoria.POSTRE;
            default -> com.helloworld.restaurant.model.Plato.Categoria.PRIMER_PLATO;
        };

        return new com.helloworld.restaurant.model.Plato(
                plato.id(),
                plato.nombre(),
                plato.precio(),
                categoria,
                plato.calorias()
        );
    }
}
