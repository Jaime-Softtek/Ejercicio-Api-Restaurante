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
}
