package com.helloworld.restaurant.services.menu.filter;

import com.helloworld.restaurant.model.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LowCost implements MenuFilter {

    @Override
    public List<Menu> apply(List<Menu> menus) {

        double media = menus.stream()
                .mapToDouble(Menu::getPrecioTotal)
                .average()
                .orElse(0);

        return menus.stream()
                .filter(menu -> menu.getPrecioTotal() <= media)
                .toList();
    }
}