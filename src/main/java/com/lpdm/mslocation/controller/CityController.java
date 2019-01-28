package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.CityDao;
import com.lpdm.mslocation.dao.DepartmentDao;
import com.lpdm.mslocation.dao.RegionDao;
import com.lpdm.mslocation.entity.City;
import com.lpdm.mslocation.exception.AddressNotFound;
import com.lpdm.mslocation.exception.CityNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vianney
 * @version 1.0
 * @since 01/01/2019
 */

@RestController
public class CityController {
    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CityDao cityDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RegionDao regionDao;

    /**
     * Call this method to get an {@link List<City>}
     * @return An {@link List<City>} json object
     */
    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCities(){
        log.info("CityController -> méthode listCities : entrée ");
        List<City> list = cityDao.findAll();

        if(list == null){
            log.warn("CityController -> méthode listCities : list null");
            throw new CityNotFound("Aucune ville trouvé dans la bdd ");
        }

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        log.info("CityController -> méthode listCities : sortie ");
        return list;
    }

    /**
     * Find {@link List<City>} by the City {@link String} zipcode
     * @param zipCode The {@link City} {@link String} zipCode
     * @return an {@link List<City>} json object
     */
    @GetMapping(value = "/cities/zipcode/{zipcode}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCitiesByZipCode(@PathVariable("zipcode") String zipCode){
        log.info("CityController -> méthode listCitiesByZipCode : entrée ");
        log.info("CityController -> méthode listCitiesByZipCode : zipcode envoyé = "+zipCode);
        List<City> list = cityDao.findByZipCode(zipCode);

        if(list == null){
            log.warn("CityController -> méthode listCitiesByZipCode : list null");
            throw new CityNotFound("Aucune ville trouvé dans la bdd avec le zipcode = "+zipCode);
        }

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        log.info("CityController -> méthode listCitiesByZipCode : sortie ");
        return list;
    }

    /**
     * Find {@link List<City>} by the City {@link String} name
     * @param name The {@link City} {@link String} name
     * @return an {@link List<City>} json object
     */
    @GetMapping(value = "/cities/name/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCitiesByName(@PathVariable String name){
        log.info("CityController -> méthode listCitiesByName : entrée ");
        log.info("CityController -> méthode listCitiesByName : name envoyé = "+name);
        List<City> list = cityDao.findAllByNameContainingIgnoreCase(name);

        if(list == null){
            log.warn("CityController -> méthode listCitiesByName : list null");
            throw new CityNotFound("Aucune ville trouvé dans la bdd avec le nom = "+name);
        }

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        log.info("CityController -> méthode listCitiesByName : sortie ");
        return list;
    }

    /**
     * Find {@link City} by the city {@link Integer} id
     * @param id The {@link City} {@link Integer} id
     * @return an {@link City} json object
     */
    @GetMapping(value = "/cities/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public City cityById(@PathVariable int id){
        log.info("CityController -> méthode cityById : entrée ");
        log.info("CityController -> méthode cityById : id envoyé = "+id);

        City city = cityDao.findById(id);

        if(city == null){
            log.warn("CityController -> méthode cityById : ville null");
            throw new AddressNotFound("Aucune ville trouvé dans la bdd avec l'id = "+id);
        }

        city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
        city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));

        log.info("CityController -> méthode cityById : sortie ");
        return city;
    }
}
