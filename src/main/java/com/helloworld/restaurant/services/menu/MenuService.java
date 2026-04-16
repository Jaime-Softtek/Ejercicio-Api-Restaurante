package com.helloworld.restaurant.services.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.services.menu.filter.MenuFilter;

import java.util.List;

public interface MenuService {

    List<Menu> getMenus();

    Menu getOneRandomMenu();

    List<Menu> getMenusByRestaurant(String cif);

    List<Menu> getMenusFilteredByRestaurant(String cif, MenuFilter filter);
}
