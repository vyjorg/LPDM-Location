package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.AdressDao;
import com.lpdm.mslocation.entity.Adress;
import com.lpdm.mslocation.exception.AdressNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdressController {
    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private AdressDao adressDao;

    @Autowired
    private CityController cityController;

    @GetMapping(value = "/adress/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Adress findAdressById(@PathVariable int id){
        log.info("AdressController -> méthode findAdressById : entrée ");
        log.info("AdressController -> méthode findAdressById : id envoyé = "+id);

        Adress adress = adressDao.findById(id);
        if(adress == null){
            log.warn("AdressController -> méthode findAdressById : adress null ");
            throw new AdressNotFound("Aucune adresse trouvé pour l'id = "+id);
        }

        adress.setCity(cityController.cityById(adress.getCityId()));



        log.info("AdressController -> méthode findAdressById : sortie ");
        return adress;
    }

    @PostMapping(value = "/adress", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addAdress(@RequestBody Adress adress){
        log.info("AdressController -> méthode addAdress : entrée ");
        log.info("AdressController -> méthode addAdress : adress envoyé = "+adress.toString());

        adress.setCityId(adress.getCity().getId());
        adressDao.save(adress);

        log.info("AdressController -> méthode addAdress : sortie ");
    }

    @DeleteMapping(value="/adress/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteAdress(@PathVariable int id){
        log.info("AdressController -> méthode deleteAdress : entrée ");
        log.info("AdressController -> méthode deleteAdress : id envoyé = "+id);

        adressDao.deleteById(id);

        log.info("AdressController -> méthode deleteAdress : entrée ");
    }

    @PutMapping(value="/adress", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateAdress(@RequestBody Adress adress){
        log.info("AdressController -> méthode updateAdress : entrée ");
        log.info("AdressController -> méthode updateAdress : adress envoyé = "+adress.toString());

        adress.setCityId(adress.getCity().getId());
        adressDao.save(adress);

        log.info("ProductController -> méthode updateProduct : sortie ");
    }
}
