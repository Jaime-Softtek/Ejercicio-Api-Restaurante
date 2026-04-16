package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.util.Locale.filter;

@Repository
public class PlatoDaoImpl implements PlatoDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private RowMapper<Plato> platoRowMapper = (rs, rowNum) ->
	{
		int id = rs.getInt("id");
		String nombre = rs.getString("nombre");
		double precio = rs.getDouble("precio");
		int categoria = rs.getInt("categoria");
		int calorias = rs.getInt("calorias");

		return new Plato(id, nombre, precio, categoria, calorias);
	};

	public PlatoDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Plato> getPlatos() {
		String query = "SELECT id, nombre, precio, categoria, calorias FROM plato";
		return jdbcTemplate.query(query, platoRowMapper);
	}

	@Override
	public Optional<Plato> getPlatosById(int platoId) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", platoId);
		String query = "SELECT id, nombre, precio, categoria, calorias FROM plato WHERE id = :id";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, params, platoRowMapper));
		}catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	//Platos con un numero inferior de calorías al pasado por querystring (inventamos el path)
	@Override
	public List<Plato> getPlatosByCalorias(int calorias) {

		Map<String, Object> params = new HashMap<>();
		params.put("calorias", calorias);
		String query = "SELECT id, nombre, precio, categoria, calorias FROM plato WHERE calorias < :calorias";
		try {
			return jdbcTemplate.query(query, params, platoRowMapper);
		}catch(EmptyResultDataAccessException e) {
			return List.of();
		}
	}

	@Override
	public List<Plato> getPlatosByCategoria(int categoria) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoria", categoria);
		String query = "SELECT id, nombre, precio, categoria, calorias FROM plato WHERE categoria = :categoria";
		try {
			return jdbcTemplate.query(query, params, platoRowMapper);
		}
		catch(EmptyResultDataAccessException e) {
			return List.of();
		}
	}

	@Override
	public Optional<Plato> updatePlato(int id, Plato plato ) {
		Map<String, Object> params = new HashMap<>();

		params.put("categoria", plato.categoria());
		params.put("nombre", plato.nombre());
		params.put("precio", plato.precio());
		params.put("calorias", plato.calorias());
		String query = "UPDATE plato SET nombre=:nombre, precio=:precio, categoria=:categoria, calorias=:calorias WHERE id = :id";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, params, platoRowMapper));
		}
		catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Plato> deletePlato(int id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		String query = "DELETE FROM plato WHERE id = :id";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, params, platoRowMapper));
		}
		catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}

	}
    @Override
    public List<Plato> getPlatosByRestaurant(String cif) {
        Map<String, Object> params = new HashMap<>();
        params.put("cif", cif);
        String query = "SELECT plato.id, plato.nombre, plato.precio, plato.categoria, plato.calorias FROM plato JOIN restaurante_plato ON plato.id = restaurante_plato.id_plato WHERE restaurante_plato.cif_restaurante = :cif";
        try {
            return jdbcTemplate.query(query, params, platoRowMapper);
        }
        catch(EmptyResultDataAccessException e) {
            return List.of();
        }
    }

	@Override
	public Optional<Plato> cretePlato(Plato plato) {
		Map<String, Object> params = new HashMap<>();


		params.put("nombre", plato.nombre());
		params.put("precio", plato.precio());
		params.put("categoria", plato.categoria());
		params.put("calorias", plato.calorias());

		String query = "INSERT INTO plato (nombre, precio, categoria, calorias) VALUES (:nombre, :precio, :categoria, :calorias)";

		try {
			return Optional.of(jdbcTemplate.queryForObject(query, params, platoRowMapper));
		}
		catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}

	}


}
