package com.helloworld.restaurant.model;

import lombok.Data;

import java.util.List;

@Data
public class Restaurante {
    private String cif;
    private String nombre;
    private String direccion;
    private int telefono;
    private List<Plato> carta;

    public Restaurante(String cif, String nombre, String direccion, int telefono, List<Plato> carta) {
        this.cif = cif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.carta = carta;
    }

    public static Restaurante fromRestauranteDao(com.helloworld.restaurant.daos.model.Restaurante restaurante){
        return new Restaurante(
                restaurante.cif(),
                restaurante.nombre(),
                restaurante.direccion(),
                restaurante.telefono(),
                restaurante.carta()
                        .stream()
                        .map(Plato::fromPlatoDAO)
                        .toList()
        );
    }

    public static com.helloworld.restaurant.daos.model.Restaurante fromRestauranteModel (Restaurante restaurante) {
        return new com.helloworld.restaurant.daos.model.Restaurante(
                restaurante.getCif(),
                restaurante.getNombre(),
                restaurante.getDireccion(),
                restaurante.getTelefono(),
                restaurante.getCarta() == null || restaurante.getCarta().isEmpty()
                        ? List.of()
                        : restaurante.getCarta()
                        .stream()
                        .map(Plato::toPlatoDAO)
                        .toList()
        );
    }
}
