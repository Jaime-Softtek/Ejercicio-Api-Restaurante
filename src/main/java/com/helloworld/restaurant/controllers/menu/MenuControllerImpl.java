package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.services.menu.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("restaurante/menus")
public class MenuControllerImpl implements MenuController{

    private final MenuService menuService;

    public MenuControllerImpl(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    @GetMapping("")
    public List<Menu> getMenus() {

        return menuService.getMenus();
    }

    @Override
    @GetMapping("/one-menu")
    public Menu getOneMenu() {

        return menuService.getOneRandomMenu();
    }
    @Override
    @GetMapping("/low-cost")
    public List<Menu> getLowCostMenus() {

        return menuService.getLowCostMenus();
    }

    @Override
    @GetMapping("/healthy")
    public List<Menu> getHealthyMenus() {
        return menuService.getHealthyMenus();
    }

}
