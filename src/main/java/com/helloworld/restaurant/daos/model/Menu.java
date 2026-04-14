package com.helloworld.restaurant.daos.model;



public record Menu(Plato primerPlato, Plato segundoPlato, Plato tercerPlato, double precio) {
    public Menu(Plato primerPlato, Plato segundoPlato, Plato tercerPlato) {
        this(primerPlato, segundoPlato, tercerPlato, primerPlato.precio() + segundoPlato.precio() + tercerPlato.precio());
    }
}
