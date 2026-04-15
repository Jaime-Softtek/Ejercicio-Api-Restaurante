package com.helloworld.restaurant.services.menu;

import com.helloworld.restaurant.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenus();
    Menu getOneRandomMenu();
    List<Menu> getLowCostMenus();
    List<Menu> getHealthyMenus();
    List<Menu> getMenusByRestaurant(String cif);
    List<Menu> getLowCostMenus(String cif);
    List<Menu> getHealthyMenus(String cif);
}
