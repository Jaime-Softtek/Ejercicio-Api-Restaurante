package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.services.menu.MenuService;
import com.helloworld.restaurant.services.menu.filter.Healthy;
import com.helloworld.restaurant.services.menu.filter.LowCost;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurante/menus")
public class MenuControllerImpl {

    private final MenuService menuService;
    private final LowCost lowCost;
    private final Healthy healthy;

    public MenuControllerImpl(MenuService menuService,
                              LowCost lowCost,
                              Healthy healthy) {
        this.menuService = menuService;
        this.lowCost = lowCost;
        this.healthy = healthy;
    }

    @GetMapping("")
    public List<Menu> getMenus() {
        return menuService.getMenus();
    }

    @GetMapping("/one-menu")
    public Menu getOneMenu() {
        return menuService.getOneRandomMenu();
    }

    @GetMapping("/{cif}")
    public List<Menu> getMenusByRestaurant(@PathVariable String cif) {
        return menuService.getMenusByRestaurant(cif);
    }

    @GetMapping("/{cif}/low-cost")
    public List<Menu> lowCost(@PathVariable String cif) {
        return menuService.getMenusFilteredByRestaurant(cif, lowCost);
    }

    @GetMapping("/{cif}/healthy")
    public List<Menu> healthy(@PathVariable String cif) {
        return menuService.getMenusFilteredByRestaurant(cif, healthy);
    }
}
