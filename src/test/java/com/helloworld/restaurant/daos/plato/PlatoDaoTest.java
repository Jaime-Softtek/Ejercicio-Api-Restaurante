package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PlatoDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatoDao platoDao;

    @Test
    void deberiaObtenerTodosLosPlatos(){

        List<Plato> platos = platoDao.getPlatos();
        assert !platos.isEmpty();
        assertEquals("Ensalada", platos.get(0).nombre());

    }

    @Test
    void deberiaObtenerPlatoPorId(){

        List<Plato> platos = platoDao.getPlatos();
        Plato plato = platoDao.getPlatosById(1).orElseThrow();
        assertEquals(platos.get(0).nombre(), plato.nombre());

    }

    @Test
    void deberiaObtenerPlatosDeUnaSolaCategoria(){

        List<Plato> platos = platoDao.getPlatosByCategoria(1);

        assertEquals(1, platos.get(0).categoria());
        assertEquals( platos.get(0).categoria(), platos.get(platos.size()-1).categoria());

    }

    @Test
    void deberiaObtenerPlatosConCaloriasInferioresA500(){

        List<Plato> platos = platoDao.getPlatosByCalorias(500);
        assert platos.stream().allMatch(plato -> plato.calorias() < 500);

    }

    @Test
    void deberiaActualizarPlato(){
        Plato plato = new Plato(1, "Filete de ternera de Wagyu", 7.99, 1, 450);
        platoDao.updatePlato(plato.id(), plato);

        assertEquals(plato.nombre(), platoDao.getPlatosById(plato.id()).orElseThrow().nombre());

    }

    @Test
    void deberiaDevolverPlatosDeRestaurante(){

        final String CIF = "A12345678";

        List<Plato> platos = platoDao.getPlatosByRestaurant(CIF);

        assertEquals("Ensalada", platos.get(0).nombre());
        assertEquals("Gazpacho", platos.get(1).nombre());

    }

    @Test
    void deberiaCrearPlato(){

        List<Plato> platos = platoDao.getPlatos();
        Plato plato = new Plato(platos.size()+1, "Prueba", 7.99, 1, 450);
        platoDao.cretePlato(plato);
        assertEquals(plato.nombre(), platoDao.getPlatosById(plato.id()).orElseThrow().nombre());
    }

    @Test
    void deberiaEliminarPlato(){

        List<Plato> platos = platoDao.getPlatos();
        Plato plato = new Plato(platos.size()+1, "Prueba", 7.99, 1, 450);
        platoDao.cretePlato(plato);

        platoDao.deletePlato(plato.id());
        assert platoDao.getPlatosById(plato.id()).isEmpty();

    }

}
