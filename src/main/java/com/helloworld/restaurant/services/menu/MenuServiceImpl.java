package com.helloworld.restaurant.services.menu;

import com.helloworld.restaurant.daos.MenuDao.MenuDao;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.services.menu.filter.MenuFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;
    private final PlatoDao platoDao;

    public MenuServiceImpl(MenuDao menuDao, PlatoDao platoDao) {
        this.menuDao = menuDao;
        this.platoDao = platoDao;
    }

    @Override
    public List<Menu> getMenus() {
        return menuDao.getMenus()
                .stream()
                .map(Menu::fromMenuDAO)
                .toList();
    }

    @Override
    public Menu getOneRandomMenu() {
        return menuDao.getOneRandomMenu()
                .map(Menu::fromMenuDAO)
                .orElse(null);
    }

    @Override
    public List<Menu> getMenusByRestaurant(String cif) {

        List<Plato> platos = platoDao.getPlatosByRestaurant(cif);

        List<Plato> primeros = platos.stream()
                .filter(p -> p.categoria() == 1)
                .toList();

        List<Plato> segundos = platos.stream()
                .filter(p -> p.categoria() == 2)
                .toList();

        List<Plato> terceros = platos.stream()
                .filter(p -> p.categoria() == 3)
                .toList();

        List<Menu> menus = new ArrayList<>();

        for (Plato p1 : primeros)
            for (Plato p2 : segundos)
                for (Plato p3 : terceros)
                    menus.add(new Menu(p1, p2, p3));

        return menus;
    }

    @Override
    public List<Menu> getMenusFilteredByRestaurant(String cif, MenuFilter filter) {
        return filter.apply(getMenusByRestaurant(cif));
    }
}
