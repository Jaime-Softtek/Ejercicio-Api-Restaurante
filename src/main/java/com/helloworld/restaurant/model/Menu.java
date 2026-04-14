package com.helloworld.restaurant.model;

import lombok.Data;

@Data
public class Menu {
    private com.helloworld.restaurant.daos.model.Plato primero;
    private com.helloworld.restaurant.daos.model.Plato segundo;
    private com.helloworld.restaurant.daos.model.Plato tercero;

    public Menu(com.helloworld.restaurant.daos.model.Plato primero, com.helloworld.restaurant.daos.model.Plato segundo, com.helloworld.restaurant.daos.model.Plato tercero) {
        this.primero = primero;
        this.segundo = segundo;
        this.tercero = tercero;
    }



    public static Menu fromMenuDAO(com.helloworld.restaurant.daos.model.Menu menu) {

        return new Menu(
                menu.primerPlato(),
                menu.segundoPlato(),
                menu.tercerPlato()
        );
    }
}
