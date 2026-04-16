package com.helloworld.restaurant.services.menu.filter;

import com.helloworld.restaurant.model.Menu;

import java.util.List;

public interface MenuFilter {
    List<Menu> apply(List<Menu> menus);
}
