package com.helloworld.restaurant.services.menu;

import com.helloworld.restaurant.daos.MenuDao.MenuDao;
import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.Plato;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;

    public MenuServiceImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List<Menu> getMenus() {
        return menuDao.getMenus().stream().map(Menu::fromMenuDAO).toList();
    }


    @Override
    public Menu getOneRandomMenu() {
        return menuDao.getOneRandomMenu().map(Menu::fromMenuDAO).orElse(null);
    }

    @Override
    public List<Menu> getLowCostMenus() {
        return menuDao.getLowCostMenus().stream().map(Menu::fromMenuDAO).toList();
    }

    @Override
    public List<Menu> getHealthyMenus() {
        List<Menu> menus = getMenus();
        double mediaCalorias = menus.stream()
                .mapToInt(Menu::getCaloriasTotales)
                .average()
                .orElse(0);

        return menus.stream()
                .filter(menu -> menu.getCaloriasTotales() < mediaCalorias)
                .toList();
    }

    @Override
    public List<Menu> getMenusByRestaurant(String cif) {
        return menuDao.getMenusByRestaurant(cif)
                .stream()
                .map(Menu::fromMenuDAO)
                .toList();
    }

    @Override
    public List<Menu> getLowCostMenus(String cif) {

        List<Menu> menus = getMenusByRestaurant(cif);

        double media = menus.stream()
                .mapToDouble(Menu::getPrecioTotal)
                .average()
                .orElse(0);

        return menus.stream()
                .filter(menu -> menu.getPrecioTotal() <= media)
                .toList();
    }
    @Override
    public List<Menu> getHealthyMenus(String cif) {

        List<Menu> menus = getMenusByRestaurant(cif);

        double mediaCalorias = menus.stream()
                .mapToInt(Menu::getCaloriasTotales)
                .average()
                .orElse(0);

        return menus.stream()
                .filter(menu -> menu.getCaloriasTotales() < mediaCalorias)
                .toList();
    }
}
