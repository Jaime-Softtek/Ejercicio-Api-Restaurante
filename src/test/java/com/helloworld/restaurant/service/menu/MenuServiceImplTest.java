package com.helloworld.restaurant.service.menu;

import com.helloworld.restaurant.daos.MenuDao.MenuDao;
import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.services.menu.MenuServiceImpl;
import com.helloworld.restaurant.services.menu.filter.Healthy;
import com.helloworld.restaurant.services.menu.filter.LowCost;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock
    private MenuDao menuDao;

    @Mock
    private PlatoDao platoDao;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Plato plato(double precio, int calorias, int categoria) {
        return new Plato(1, "plato", precio, categoria, calorias);
    }

    private com.helloworld.restaurant.daos.model.Menu menuDAO() {
        return new com.helloworld.restaurant.daos.model.Menu(
                plato(5, 200, 1),
                plato(6, 300, 2),
                plato(7, 400, 3)
        );
    }

    @Test
    void getMenus_shouldReturnList() {

        Mockito.when(menuDao.getMenus())
                .thenReturn(List.of(menuDAO()));

        var result = menuService.getMenus();

        assertEquals(1, result.size());
        assertEquals(18.0, result.get(0).getPrecioTotal());
    }

    @Test
    void getOneRandomMenu_shouldReturnMenu() {

        Mockito.when(menuDao.getOneRandomMenu())
                .thenReturn(Optional.of(menuDAO()));

        var result = menuService.getOneRandomMenu();

        assertNotNull(result);
        assertEquals(18.0, result.getPrecioTotal());
    }

    @Test
    void getOneRandomMenu_shouldReturnNull_whenEmpty() {

        Mockito.when(menuDao.getOneRandomMenu())
                .thenReturn(Optional.empty());

        var result = menuService.getOneRandomMenu();

        assertNull(result);
    }

    @Test
    void getMenusByRestaurant_shouldBuildMenusCorrectly() {

        String cif = "A1";

        Mockito.when(platoDao.getPlatosByRestaurant(cif))
                .thenReturn(List.of(
                        plato(5, 200, 1),
                        plato(6, 300, 2),
                        plato(7, 400, 3)
                ));

        var result = menuService.getMenusByRestaurant(cif);

        assertEquals(1, result.size());
        assertEquals(18.0, result.get(0).getPrecioTotal());
    }

    @Test
    void getLowCostFilter_shouldReturnBelowAverage() {

        String cif = "A1";

        Mockito.when(platoDao.getPlatosByRestaurant(cif))
                .thenReturn(List.of(
                        plato(1, 100, 1),
                        plato(1, 100, 2),
                        plato(1, 100, 3),
                        plato(10, 100, 1),
                        plato(10, 100, 2),
                        plato(10, 100, 3)
                ));

        var result = menuService.getMenusFilteredByRestaurant(cif, new LowCost());

        assertEquals(1, result.size());
        assertEquals(3.0, result.get(0).getPrecioTotal());
    }

    @Test
    void getHealthyFilter_shouldReturnBelowAverageCalories() {

        String cif = "A1";

        Mockito.when(platoDao.getPlatosByRestaurant(cif))
                .thenReturn(List.of(
                        plato(5, 100, 1),
                        plato(5, 100, 2),
                        plato(5, 100, 3),
                        plato(5, 500, 1),
                        plato(5, 500, 2),
                        plato(5, 500, 3)
                ));

        var result = menuService.getMenusFilteredByRestaurant(cif, new Healthy());

        assertEquals(1, result.size());
        assertEquals(300, result.get(0).getCaloriasTotales());
    }
}