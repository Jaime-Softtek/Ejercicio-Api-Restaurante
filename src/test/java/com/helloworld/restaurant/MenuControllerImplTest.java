package com.helloworld.restaurant;

import com.helloworld.restaurant.controllers.menu.MenuControllerImpl;
import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.services.menu.MenuService;
import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.services.menu.filter.Healthy;
import com.helloworld.restaurant.services.menu.filter.LowCost;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuControllerImpl.class)
class MenuControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuService menuService;

    private Plato plato(double precio, int calorias) {
        return new Plato(1, "plato", precio, 1, calorias);
    }

    private Menu menu() {
        return new Menu(
                plato(5, 200),
                plato(6, 300),
                plato(7, 400)
        );
    }

    @Test
    void getMenus_shouldReturnList() throws Exception {

        Mockito.when(menuService.getMenus())
                .thenReturn(List.of(menu(), menu()));

        mockMvc.perform(get("/restaurante/menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getOneMenu_shouldReturnMenu() throws Exception {

        Mockito.when(menuService.getOneRandomMenu())
                .thenReturn(menu());

        mockMvc.perform(get("/restaurante/menus/one-menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precioTotal").value(18.0));
    }

    @Test
    void getMenusByRestaurant_shouldReturnList() throws Exception {

        Mockito.when(menuService.getMenusByRestaurant("A1"))
                .thenReturn(List.of(menu()));

        mockMvc.perform(get("/restaurante/menus/A1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void getLowCostByRestaurant_shouldReturnList() throws Exception {

        Mockito.when(menuService.getMenusFilteredByRestaurant(Mockito.eq("A1"), Mockito.any()))
                .thenReturn(List.of(menu()));

        mockMvc.perform(get("/restaurante/menus/A1/low-cost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getHealthyByRestaurant_shouldReturnList() throws Exception {

        Mockito.when(menuService.getMenusFilteredByRestaurant(Mockito.eq("A1"), Mockito.any()))
                .thenReturn(List.of(menu()));

        mockMvc.perform(get("/restaurante/menus/A1/healthy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
