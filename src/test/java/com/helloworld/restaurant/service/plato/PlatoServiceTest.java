package com.helloworld.restaurant.service.plato;

import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.services.plato.PlatoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

}
