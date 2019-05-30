package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.CityDao;
import com.lpdm.mslocation.dao.DepartmentDao;
import com.lpdm.mslocation.dao.RegionDao;
import com.lpdm.mslocation.entity.City;
import com.lpdm.mslocation.exception.CityNotFound;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(description="Controller pour les opérations CRUD sur les villes.")
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
    @ApiOperation(value = "Récupère toutes les villes de la bdd")
    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCities(){
        
        List<City> list = cityDao.findAll();

        if(list == null){
            
            throw new CityNotFound("Aucune ville trouvé dans la bdd ");
        }

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        return list;
    }

    /**
     * Find {@link List<City>} by the City {@link String} zipcode
     * @param zipCode The {@link City} {@link String} zipCode
     * @return an {@link List<City>} json object
     */
    @ApiOperation(value = "Récupère les villes grâce à leurs code postale")
    @GetMapping(value = "/cities/zipcode/{zipcode}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCitiesByZipCode(@PathVariable("zipcode") String zipCode){

        List<City> list = cityDao.findByZipCode(zipCode);

        if(list == null){

            throw new CityNotFound("Aucune ville trouvé dans la bdd avec le zipcode = "+zipCode);
        }

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }


        return list;
    }

    /**
     * Find {@link List<City>} by the City {@link String} name
     * @param name The {@link City} {@link String} name
     * @return an {@link List<City>} json object
     */
    @ApiOperation(value = "Récupère les villes grâce à leurs nom")
    @GetMapping(value = "/cities/name/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCitiesByName(@PathVariable String name){

        List<City> list = cityDao.findAllByNameContainingIgnoreCase(name);

        if(list == null){

            throw new CityNotFound("Aucune ville trouvé dans la bdd avec le nom = "+name);
        }

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        return list;
    }

    /**
     * Find {@link City} by the city {@link Integer} id
     * @param id The {@link City} {@link Integer} id
     * @return an {@link City} json object
     */
    @ApiOperation(value = "Récupère une ville grâce à son ID si celui-ci existe dans la bdd")
    @GetMapping(value = "/cities/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public City cityById(@PathVariable int id){


        City city = cityDao.findById(id);

        if(city == null){

            throw new CityNotFound("Aucune ville trouvé dans la bdd avec l'id = "+id);
        }

        city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
        city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));

        return city;
    }
}
