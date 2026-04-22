package com.helloworld.restaurant.services.plato;

import com.helloworld.restaurant.config.LabSoapConfig;
import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.mapper.Mapper;
import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurante.lab.GetPlatoRdnReq;
import com.helloworld.restaurante.lab.GetPlatoRdnRes;
import com.helloworld.restaurante.lab.NotFoundFault_Exception;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlatoSoapServiceImpl implements PlatoSoapService {
    private final LabSoapConfig labSoapConfig;
    private final Mapper<GetPlatoRdnRes, com.helloworld.restaurant.daos.model.Plato> soapToPlatoDao;
    private final Mapper<com.helloworld.restaurant.daos.model.Plato, Plato> daoToPlato;
    private final PlatoDao platoDao;

    public PlatoSoapServiceImpl(LabSoapConfig labSoapConfig, Mapper<GetPlatoRdnRes, com.helloworld.restaurant.daos.model.Plato> soapToPlatoDao, Mapper<com.helloworld.restaurant.daos.model.Plato, Plato> daoToPlato, PlatoDao platoDao) {
        this.labSoapConfig = labSoapConfig;
        this.soapToPlatoDao = soapToPlatoDao;
        this.daoToPlato = daoToPlato;
        this.platoDao = platoDao;
    }

    public Optional<Plato> getRandomPlatoFromSoap() throws NotFoundFault_Exception {
        var platoFromSoapServer = labSoapConfig
                .labWSClient()
                .getPlatoRdn(new GetPlatoRdnReq());
        var plato = soapToPlatoDao.map(platoFromSoapServer);
        var doesThisPlatoExists = platoDao.findPlatoByParameters(plato);
        if (doesThisPlatoExists.isEmpty()){
            System.out.println("El plato no existía previamente");
            var newPlato = platoDao.cretePlato(plato);
            return newPlato.map(daoToPlato::map);
        }

        return doesThisPlatoExists.map(Plato::fromPlatoDAO);
    }
}
