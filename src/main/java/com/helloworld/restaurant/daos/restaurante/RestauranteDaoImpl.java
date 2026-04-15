package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.daos.model.Restaurante;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.daos.plato.PlatoDaoImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
