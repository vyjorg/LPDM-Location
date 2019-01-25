package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.AddressDao;
import com.lpdm.mslocation.entity.Address;
import com.lpdm.mslocation.exception.AddressNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {
    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CityController cityController;

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

    @PostMapping(value = "/address", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address addAddress(@RequestBody Address address){
        log.info("AddressController -> méthode addAdress : entrée ");
        log.info("AddressController -> méthode addAdress : address envoyé = "+address.toString());

        address.setCityId(address.getCity().getId());
        Address addressAdded = addressDao.save(address);

        log.info("AddressController -> méthode addAdress : sortie ");
        return addressAdded;
    }

    @DeleteMapping(value="/address/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteAddress(@PathVariable int id){
        log.info("AddressController -> méthode deleteAddress : entrée ");
        log.info("AddressController -> méthode deleteAddress : id envoyé = "+id);

        addressDao.deleteById(id);

        log.info("AddressController -> méthode deleteAddress : entrée ");
    }

    @PutMapping(value="/address", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateAdress(@RequestBody Address adress){
        log.info("AddressController -> méthode updateAddress : entrée ");
        log.info("AddressController -> méthode updateAddress : adress envoyé = "+adress.toString());

        adress.setCityId(adress.getCity().getId());
        addressDao.save(adress);

        log.info("AddressController -> méthode updateAddress : entrée ");
    }
}
