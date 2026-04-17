package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.daos.model.Restaurante;
import com.helloworld.restaurant.daos.plato.PlatoDaoImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.DataAccessException;

@Repository
public class RestauranteDaoImpl implements RestauranteDao{

    private NamedParameterJdbcTemplate jdbcTemplate;
    private PlatoDaoImpl platoDao;

    private RowMapper<Restaurante> restauranteRowMapper = (rs, rowNum) ->
    {
        String cif = rs.getString("cif");
        String nombre = rs.getString("nombre");
        String direccion = rs.getString("direccion");
        int telefono = rs.getInt("telefono");

        return new Restaurante(cif, nombre, direccion, telefono, platoDao.getPlatosByRestaurant(cif));
    };

    public RestauranteDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, PlatoDaoImpl platoDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.platoDao = platoDao;
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        String query = "SELECT cif, nombre, direccion, telefono FROM restaurante";
        return jdbcTemplate.query(query, restauranteRowMapper);
    }

    @Override
    public Optional<Restaurante> getRestauranteByCif(String cif) {
        Map<String, Object> params = new HashMap<>();
        params.put("cif", cif);
        String query = "SELECT cif, nombre, direccion, telefono FROM restaurante WHERE cif=:cif";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, params, restauranteRowMapper));
        } catch (EmptyResultDataAccessException | NullPointerException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Restaurante> saveRestaurante(Restaurante newRestaurante) {
        String query = "INSERT INTO restaurante (cif, nombre, direccion, telefono) " +
                "VALUES (:newCif, :newNombre, :newDireccion, :newTelefono)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("newCif", newRestaurante.cif())
                .addValue("newNombre", newRestaurante.nombre())
                .addValue("newDireccion", newRestaurante.direccion())
                .addValue("newTelefono", newRestaurante.telefono());

        int filas = jdbcTemplate.update(query, params);
        if (filas == 1) {
            Restaurante res = new Restaurante(newRestaurante.cif(), newRestaurante.nombre(), newRestaurante.direccion(), newRestaurante.telefono(), List.of());
            return Optional.of(res);
        } else {
            return Optional.empty();
        }

    }

    @Override
    public boolean eliminarRestaurante(String cifToDel) {
        if (cifToDel == null || cifToDel.trim().isEmpty()) {
            return false;
        }

        String query = "DELETE from restaurante where cif=:cifToDel";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("cifToDel", cifToDel.trim());

        int filas = jdbcTemplate.update(query, params);
        return filas == 1;
    }

    @Override
    public Optional<Restaurante> editRestaurante(String cif, Restaurante restaurante) {
        String query = "UPDATE restaurante SET " +
                "nombre=:newNombre, " +
                "direccion=:newDireccion, " +
                "telefono=:newTelefono " +
                "WHERE cif=:cif";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("cif", cif)
                .addValue("newNombre", restaurante.nombre())
                .addValue("newDireccion", restaurante.direccion())
                .addValue("newTelefono", restaurante.telefono());


        int filas = jdbcTemplate.update(query, params);

        if (filas == 0) {
            return Optional.empty();
        }

        String selectQuery = "SELECT * FROM restaurante WHERE cif=:cif";
        MapSqlParameterSource selectParams = new MapSqlParameterSource().addValue("cif", cif);

        try {
            return Optional.of(jdbcTemplate.queryForObject(selectQuery, selectParams, restauranteRowMapper));
        } catch (EmptyResultDataAccessException | NullPointerException e){
            return Optional.empty();
        }
    }

    //editar carta restaurante
    @Override
    public boolean addPlatoToRestaurante(String cif, Integer idPlato) {
        String query = "INSERT INTO restaurante_plato (id_plato, cif_restaurante) VALUES (:idPlato, :cif)";
        
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idPlato", idPlato)
                .addValue("cif", cif);
        
        try {
            int filas = jdbcTemplate.update(query, params);
            return filas == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean removePlatoFromRestaurante(String cif, Integer idPlato) {
        String query = "DELETE FROM restaurante_plato WHERE cif_restaurante=:cif AND id_plato=:idPlato";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("cif", cif)
                .addValue("idPlato", idPlato);

        try {
            int filas = jdbcTemplate.update(query, params);
            return filas == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
