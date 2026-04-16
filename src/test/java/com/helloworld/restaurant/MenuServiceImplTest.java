package com.helloworld.restaurant;

import com.helloworld.restaurant.daos.MenuDao.MenuDao;
import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.services.menu.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MenuServiceImplTest {

    private MenuDao menuDao;
    private MenuServiceImpl menuService;

    @BeforeEach
    void setUp() {
        menuDao = Mockito.mock(MenuDao.class);
        menuService = new MenuServiceImpl(menuDao);
    }

    private Plato plato(double precio, int calorias) {
        return new Plato(1, "plato", precio, 1, calorias);
    }

    private com.helloworld.restaurant.daos.model.Menu menuDAO(
            double p1, double p2, double p3,
            int c1, int c2, int c3) {

        return new com.helloworld.restaurant.daos.model.Menu(
                plato(p1, c1),
                plato(p2, c2),
                plato(p3, c3)
        );
    }

    @Test
    void getOneRandomMenu_shouldReturnMenu() {
        Mockito.when(menuDao.getOneRandomMenu())
                .thenReturn(Optional.of(menuDAO(1, 1, 1, 100, 100, 100)));

        var result = menuService.getOneRandomMenu();

        assertNotNull(result);
        assertEquals(3.0, result.getPrecioTotal());
    }

    @Test
    void getHealthyMenus_shouldReturnBelowAverageCalories() {
        Mockito.when(menuDao.getMenus())
                .thenReturn(
                        java.util.List.of(
                                menuDAO(1, 1, 1, 100, 100, 100), // 300
                                menuDAO(1, 1, 1, 300, 300, 300)  // 900
                        )
                );

        var result = menuService.getHealthyMenus();

        assertEquals(1, result.size());
        assertEquals(300, result.get(0).getCaloriasTotales());
    }

    @Test
    void getLowCostMenus_shouldReturnBelowAvereagePrice() {
        String cif = "A1";
        Mockito.when(menuDao.getMenusByRestaurant(cif))
                .thenReturn(
                        java.util.List.of(
                                menuDAO(1, 1, 1, 100, 100, 100),
                                menuDAO(10, 10, 10, 100, 100, 100)
                        )
                );
        var result = menuService.getLowCostMenus(cif);

        assertEquals(1, result.size());
        assertEquals(3.0, result.get(0).getPrecioTotal());
    }

    @Test
    void getOneRandomMenu_shouldReturnNull_whenEmpty() {
        Mockito.when(menuDao.getOneRandomMenu())
                .thenReturn(Optional.empty());

        var result = menuService.getOneRandomMenu();

        assertEquals(null, result);
    }

    @Test
    void getMenus_shouldReturnList() {
        Mockito.when(menuDao.getMenus())
                .thenReturn(
                        java.util.List.of(
                                menuDAO(1, 1, 1, 100, 100, 100)
                        )
                );

        var result = menuService.getMenus();

        assertEquals(1, result.size());
    }

}
