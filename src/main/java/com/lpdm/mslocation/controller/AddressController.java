package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.AddressDao;
import com.lpdm.mslocation.entity.Address;
import com.lpdm.mslocation.exception.AddressNotFound;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vianney
 * @version 1.0
 * @since 01/01/2019
 */

@Api(description="Controller pour les opérations CRUD sur les adresses.")
@RestController
public class AddressController {
    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CityController cityController;

    /**
     * Find {@link Address} by the address {@link Integer} id
     * @param id The {@link Address} {@link Integer} id
     * @return an {@link Address} json object
     */
    @ApiOperation(value = "Récupère une adresse grâce à son ID si celui-ci existe dans la bdd")
    @GetMapping(value = "/address/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address findAddressById(@PathVariable int id){
        log.info("AddressController -> méthode findAddressById : entrée ");
        log.info("AddressController -> méthode findAddressById : id envoyé = "+id);

        Address address = addressDao.findById(id);
        if(address == null){
            log.warn("AddressController -> méthode findAdressById : adress null ");
            throw new AddressNotFound("Aucune adresse trouvé pour l'id = "+id);
        }

        address.setCity(cityController.cityById(address.getCityId()));
        log.info("AdressController -> méthode findAdressById : sortie ");
        return address;
    }

    /**
     * Add {@link Address} in database
     * @param address {@link Address}
     * @return an {@link Address} added json object
     */
    @ApiOperation(value = "Enregistre une adresse si celle-ci est conforme")
    @PostMapping(value = "/address", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address addAddress(@Valid @RequestBody Address address){
        log.info("AddressController -> méthode addAdress : entrée ");
        log.info("AddressController -> méthode addAdress : address envoyé = "+address.toString());

        address.setCityId(address.getCity().getId());
        Address addressAdded = addressDao.save(address);

        log.info("AddressController -> méthode addAdress : sortie ");
        return addressAdded;
    }

    /**
     * Delete {@link Address} by the address {@link Integer} id
     * @param id The {@link Address} {@link Integer} id
     */
    @ApiOperation(value = "Supprime une adresse grâce à son ID si celle-ci existe dans la bdd")
    @DeleteMapping(value="/address/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteAddress(@PathVariable int id){
        log.info("AddressController -> méthode deleteAddress : entrée ");
        log.info("AddressController -> méthode deleteAddress : id envoyé = "+id);

        addressDao.deleteById(id);

        log.info("AddressController -> méthode deleteAddress : entrée ");
    }

    /**
     * Update {@link Address} in database
     * @param adress {@link Address }
     * @return {@link Address}
     */
    @ApiOperation(value = "Met à jour une adresse si celle-ci est conforme")
    @PutMapping(value="/address", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address updateAdress(@RequestBody Address adress){
        log.info("AddressController -> méthode updateAddress : entrée ");
        log.info("AddressController -> méthode updateAddress : adress envoyé = "+adress.toString());

        adress.setCityId(adress.getCity().getId());
        Address updateAddress = addressDao.save(adress);

        log.info("AddressController -> méthode updateAddress : sortie ");
        return updateAddress;
    }
}
