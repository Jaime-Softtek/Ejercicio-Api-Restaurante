package com.helloworld.restaurant.daos.MenuDao;

import com.helloworld.restaurant.daos.model.Menu;
import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.daos.plato.PlatoDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class MenuDaoImpl implements MenuDao{

    PlatoDaoImpl platoDao;

    public MenuDaoImpl(
            PlatoDaoImpl platoDao)
    {
        this.platoDao = platoDao;
    }


    @Override
    public List<Menu> getMenus() {

        List<Menu> menus = new ArrayList<>();

        List<Plato> platosCategoria1 = platoDao.getPlatosByCategoria(1);
        List<Plato> platosCategoria2 = platoDao.getPlatosByCategoria(2);
        List<Plato> platosCategoria3 = platoDao.getPlatosByCategoria(3);

        for (Plato primero : platosCategoria1){
            for (Plato segundo : platosCategoria2){
                for (Plato tercero : platosCategoria3){

                    menus.add(new Menu(primero, segundo, tercero));

                }
            }
        }
        return menus;
    }
    @Override
    public Optional<Menu> getOneRandomMenu() {
        Random random = new Random();

        return Optional.ofNullable(getMenus().get(random.nextInt(getMenus().size())));

    }

    @Override
    public List<Menu> getLowCostMenus() {

        int preciomedio = getMenus().stream()
                .mapToInt(menu -> (int) menu.precio())
                .sum() / getMenus().size();

        return getMenus().stream()
                .filter(menu -> menu.precio() <= preciomedio)
                .toList();
    }
}
