package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest(properties = "spring.sql.init.mode=never")
@Import(PlatoDaoImpl.class)
@Transactional
@ActiveProfiles("test")
public class PlatoDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatoDao platoDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("""
            CREATE TABLE plato (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(255),
                precio DOUBLE,
                categoria INT,
                calorias INT
            )
        """);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO plato (nombre, precio, categoria, calorias) VALUES ('Ensalada', 5.99, 1, 150)",
            "INSERT INTO plato (nombre, precio, categoria, calorias) VALUES ('Gazpacho', 4.99, 1, 200)",
            "INSERT INTO plato (nombre, precio, categoria, calorias) VALUES ('Filete de ternera', 12.99, 2, 600)"
    })
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
