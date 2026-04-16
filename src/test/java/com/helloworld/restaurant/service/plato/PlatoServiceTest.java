package com.helloworld.restaurant.service.plato;

import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.services.plato.PlatoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.helloworld.restaurant.model.Plato.Categoria.PRIMER_PLATO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlatoServiceTest {

    @Mock
    private PlatoDao platoDao;

    @InjectMocks
    private PlatoServiceImpl platoService;

    @Test
    void deberiaRetornarPlatoCuandoExiste() {

        Plato plato = new Plato(1, "Pizza", 10.5, 1, 500);

        when(platoDao.getPlatosById(1))
                .thenReturn(Optional.of(plato));

        Optional<com.helloworld.restaurant.model.Plato> resultado = platoService.getPlatosById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Pizza", resultado.get().getNombre());

        verify(platoDao).getPlatosById(1);
    }

    @Test
    void deberiaRetornarVacioCuandoNoExiste() {

        when(platoDao.getPlatosById(99))
                .thenReturn(Optional.empty());
        Optional<com.helloworld.restaurant.model.Plato> resultado = platoService.getPlatosById(99);

        assertTrue(resultado.isEmpty());

        verify(platoDao).getPlatosById(99);
    }

    @Test
    void deberiaRetornarListaDePlatosMapeadaCorrectamente() {
        List<Plato> platosDao = List.of(
                new Plato(1, "Pizza", 10.5, 1, 500),
                new Plato(2, "Hamburguesa", 8.0, 2, 700)
        );

        when(platoDao.getPlatos()).thenReturn(platosDao);

        List<com.helloworld.restaurant.model.Plato> resultado = platoService.getPlatos();

        assertEquals(2, resultado.size());
        assertEquals("Pizza", resultado.get(0).getNombre());
        assertEquals("Hamburguesa", resultado.get(1).getNombre());

        verify(platoDao).getPlatos();
    }

    @Test
    void deberiaCrearPlatoYRetornarloMapeadoCorrectamente() {
        com.helloworld.restaurant.model.Plato platoEntrada =
                new com.helloworld.restaurant.model.Plato(1, "Pizza", 10.5, PRIMER_PLATO, 500);

        Plato platoDaoCreado =
                new Plato(1, "Pizza", 10.5, 1, 500);

        when(platoDao.cretePlato(any(com.helloworld.restaurant.daos.model.Plato.class)))
                .thenReturn(Optional.of(platoDaoCreado));

        Optional<com.helloworld.restaurant.model.Plato> resultado = platoService.createPlato(platoEntrada);

        assertTrue(resultado.isPresent());
        assertEquals("Pizza", resultado.get().getNombre());
        assertEquals(500, resultado.get().getCalorias());

        verify(platoDao).cretePlato(any(com.helloworld.restaurant.daos.model.Plato.class));
    }

    @Test
    void deberiaEliminarPlatoYRetornarloSiExiste() {
        Plato platoDaoEliminado =
                new Plato(1, "Pizza", 10.5, 1, 500);

        when(platoDao.deletePlato(1)).thenReturn(Optional.of(platoDaoEliminado));

        Optional<com.helloworld.restaurant.model.Plato> resultado = platoService.deletePlato(1);

        assertTrue(resultado.isPresent());
        assertEquals("Pizza", resultado.get().getNombre());

        verify(platoDao).deletePlato(1);
    }


}
