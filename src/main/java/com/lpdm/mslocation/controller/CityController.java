package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.CityDao;
import com.lpdm.mslocation.dao.DepartmentDao;
import com.lpdm.mslocation.dao.RegionDao;
import com.lpdm.mslocation.entity.City;
import com.lpdm.mslocation.exception.AdressNotFound;
import com.lpdm.mslocation.exception.CityNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private CityDao cityDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RegionDao regionDao;

    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCities(){
        log.info("CityController -> méthode listCities : entrée ");
        List<City> list = cityDao.findAll();

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        if(list == null){
            log.warn("CityController -> méthode listCities : list null");
            throw new CityNotFound("Aucune ville trouvé dans la bdd ");
        }

        log.info("CityController -> méthode listCities : sortie ");
        return list;
    }

    @GetMapping(value = "/cities/{zipcode}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCitiesByZipCode(@PathVariable("zipcode") String zipCode){
        log.info("CityController -> méthode listCitiesByZipCode : entrée ");
        log.info("CityController -> méthode listCitiesByZipCode : zipcode envoyé = "+zipCode);
        List<City> list = cityDao.findByZipCode(zipCode);

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        if(list == null){
            log.warn("CityController -> méthode listCitiesByZipCode : list null");
            throw new CityNotFound("Aucune ville trouvé dans la bdd avec le zipcode = "+zipCode);
        }

        log.info("CityController -> méthode listCitiesByZipCode : sortie ");
        return list;
    }

    @GetMapping(value = "/cities/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCitiesByName(@PathVariable String name){
        log.info("CityController -> méthode listCitiesByName : entrée ");
        log.info("CityController -> méthode listCitiesByName : name envoyé = "+name);
        List<City> list = cityDao.findAllByNameContainingIgnoreCase(name);

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        if(list == null){
            log.warn("CityController -> méthode listCitiesByName : list null");
            throw new CityNotFound("Aucune ville trouvé dans la bdd avec le nom = "+name);
        }

        log.info("CityController -> méthode listCitiesByName : sortie ");
        return list;
    }

    @GetMapping(value = "/cities/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public City cityById(@PathVariable int id){
        log.info("CityController -> méthode cityById : entrée ");
        log.info("CityController -> méthode cityById : id envoyé = "+id);

        City city = cityDao.findById(id);
        city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
        city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));

        if(city == null){
            log.warn("CityController -> méthode cityById : ville null");
            throw new AdressNotFound("Aucune ville trouvé dans la bdd avec l'id = "+id);
        }

        log.info("CityController -> méthode cityById : sortie ");
        return city;
    }
}
