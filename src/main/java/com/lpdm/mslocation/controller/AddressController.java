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

        Address address = addressDao.findById(id);
        if(address == null){
            throw new AddressNotFound("Aucune adresse trouvé pour l'id = "+id);
        }

        address.setCity(cityController.cityById(address.getCityId()));
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

        address.setCityId(address.getCity().getId());
        Address addressAdded = addressDao.save(address);
        return addressAdded;
    }

    /**
     * Delete {@link Address} by the address {@link Integer} id
     * @param id The {@link Address} {@link Integer} id
     */
    @ApiOperation(value = "Supprime une adresse grâce à son ID si celle-ci existe dans la bdd")
    @DeleteMapping(value="/address/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteAddress(@PathVariable int id){

        addressDao.deleteById(id);
    }

    /**
     * Update {@link Address} in database
     * @param adress {@link Address }
     * @return {@link Address}
     */
    @ApiOperation(value = "Met à jour une adresse si celle-ci est conforme")
    @PutMapping(value="/address", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address updateAdress(@RequestBody Address adress){

        adress.setCityId(adress.getCity().getId());
        Address updateAddress = addressDao.save(adress);
        return updateAddress;
    }
}
