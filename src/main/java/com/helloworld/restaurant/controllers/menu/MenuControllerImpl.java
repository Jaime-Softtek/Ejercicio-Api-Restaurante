package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.services.menu.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("restaurante/menus")
public class MenuControllerImpl implements MenuController {

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

    @GetMapping("/{cif}")
    public List<Menu> getMenusByRestaurant(@PathVariable String cif) {
        return menuService.getMenusByRestaurant(cif);
    }

    @GetMapping("/{cif}/low-cost")
    public List<Menu> getLowCostMenus(@PathVariable String cif) {
        return menuService.getLowCostMenus(cif);
    }
    @GetMapping("/{cif}/healthy")
    public List<Menu> getHealthyMenus(@PathVariable String cif) {
        return menuService.getHealthyMenus(cif);
    }

}
