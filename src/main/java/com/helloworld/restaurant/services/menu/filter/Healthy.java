package com.helloworld.restaurant.services.menu.filter;

import com.helloworld.restaurant.model.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Healthy implements MenuFilter {

    @Override
    public List<Menu> apply(List<Menu> menus) {

        double mediaCalorias = menus.stream()
                .mapToInt(Menu::getCaloriasTotales)
                .average()
                .orElse(0);

        return menus.stream()
                .filter(m -> m.getCaloriasTotales() < mediaCalorias)
                .toList();
    }
}