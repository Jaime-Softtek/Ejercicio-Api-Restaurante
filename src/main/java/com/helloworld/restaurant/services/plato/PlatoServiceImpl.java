package com.helloworld.restaurant.services.plato;

import com.helloworld.restaurant.daos.MenuDao.MenuDao;
import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.model.Plato;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService {

	private final PlatoDao platoDao;



	public PlatoServiceImpl(PlatoDao platoDao, MenuDao menuDao) {
		this.platoDao = platoDao;
    }

	@Override
	public List<Plato> getPlatos() {
		return platoDao.getPlatos().stream().map(Plato::fromPlatoDAO).toList();
	}


	@Override
	public Optional<Plato> getPlatosById(int id) {
		return platoDao.getPlatosById(id).map(Plato::fromPlatoDAO);
	}

	@Override
	public List<Plato> getPlatosByCalorias(int calorias) {
		return platoDao.getPlatosByCalorias(calorias).stream().map(Plato::fromPlatoDAO).toList();
	}


	@Override
	public List<Plato> getPlatosByCategoria(int categoria) {
		return platoDao.getPlatosByCategoria(categoria).stream().map(Plato::fromPlatoDAO).toList();
	}

	@Override
	public Optional<Plato> editPlato(int id, Plato plato) {
		return platoDao.updatePlato(id, plato.toPlatoDAO()).map(Plato::fromPlatoDAO);
	}

	@Override
	public Optional<Plato> deletePlato(int id) {
		return Optional.empty();
	}


}
