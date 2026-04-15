package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;

import java.util.List;

public interface MenuController {
    List<Menu> getMenus();
    Menu getOneMenu();
    List<Menu> getLowCostMenus();
    List<Menu> getHealthyMenus();
}
