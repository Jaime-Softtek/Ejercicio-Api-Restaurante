package com.helloworld.restaurant.daos.model;

import java.util.List;

public record Restaurante(
        String cif,
        String nombre,
        String direccion,
        int telefono,
        List<Plato> carta
) {}
