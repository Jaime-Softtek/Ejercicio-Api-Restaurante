package com.helloworld.restaurant.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Restaurante {

    @Schema(description = "Número de identificación fiscal")
    @NotNull(message = "CIF del local es obligatorio")
    private String cif;
    @Schema(description = "Nombre del local")
    @NotNull(message = "Nombre del establecimiento es obligatorio")
    private String nombre;
    @Schema(description = "Dirección física del local")
    private String direccion;
    @Schema(description = "Número de telefono del local")
    private int telefono;
    @Schema(description = "Platos disponibles en el local")
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
                restaurante.getCarta()
                        .stream()
                        .map(Plato::toPlatoDAO)
                        .toList()
        );
    }
}
