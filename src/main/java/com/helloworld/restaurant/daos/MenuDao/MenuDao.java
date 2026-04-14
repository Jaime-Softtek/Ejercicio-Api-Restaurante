package com.helloworld.restaurant.daos.MenuDao;

import com.helloworld.restaurant.daos.model.Menu;
import com.helloworld.restaurant.daos.model.Plato;

import java.util.List;
import java.util.Optional;

public interface MenuDao {
    List<Menu> getMenus();

    Optional<Menu> getOneRandomMenu();
    List<Menu> getLowCostMenus();
}
