package com.helloworld.restaurant.services.restaurante;

import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.daos.model.Restaurante;
import com.helloworld.restaurant.daos.restaurante.RestauranteDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteServiceImplTest {
    @Mock
    private RestauranteDao restauranteDao;

    @InjectMocks
    private RestauranteServiceImpl restauranteService;

    @Test
    void shouldGetAllRestaurantes() {
        when(restauranteDao.getRestaurantes()).thenReturn(createRestaurantes());
        var restaurantes = restauranteService.getAllRestaurantes();

        assertEquals(1, restaurantes.size());
        assertInstanceOf(com.helloworld.restaurant.model.Restaurante.class, restaurantes.get(0));
    }

    @Test
    void ShouldReturnRestauranteByCif() {
        when(restauranteDao.getRestauranteByCif("A12345678")).thenReturn(Optional.of(createRestaurantes().get(0)));
        var restaurante = restauranteService.getRestauranteByCif("A12345678");

        assertTrue(restaurante.isPresent());
        assertInstanceOf(com.helloworld.restaurant.model.Restaurante.class, restaurante.get());
        assertEquals("A12345678", restaurante.get().getCif());

    }

    @Test
    void shouldReturnEmptyObjectIfRestaurantDoesntExists()
    {
        when(restauranteDao.getRestauranteByCif("A000000")).thenReturn(Optional.empty());
        var restaurante = restauranteService.getRestauranteByCif("A000000");

        assertTrue(restaurante.isEmpty());
    }

    @Test
    void shouldReturnCartaFromRestaurante() {
        when(restauranteDao.getRestauranteByCif("A12345678")).thenReturn(Optional.of(createRestaurantes().get(0)));
        var carta = restauranteService.getCartaFromRestaurante("A12345678");

        assertFalse(carta.isEmpty());
        assertEquals(2, carta.size());
        assertEquals("Pizza", carta.get(0).getNombre());
        assertEquals("Hamburguesa", carta.get(1).getNombre());
    }

    @Test
    void createRestaurante() {
    }

    @Test
    void deleteRestaurante() {
    }

    @Test
    void modifyRestaurante() {
    }

    private List<Restaurante> createRestaurantes() {
        return List.of(new Restaurante("A12345678", "La Mar Salada", "Calle Mayor, 1", 123456789, List.of(new Plato(1, "Pizza", 10.5, 1, 500), new Plato(2, "Hamburguesa", 8.0, 2, 700))));
    }
}