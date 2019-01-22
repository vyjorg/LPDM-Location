package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.CityDao;
import com.lpdm.mslocation.dao.DepartmentDao;
import com.lpdm.mslocation.dao.RegionDao;
import com.lpdm.mslocation.entity.City;
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

        log.info("CityController -> méthode listCities : sortie ");
        return list;
    }

    @GetMapping(value = "/cities/{zipcode}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCitiesByZipCode(@PathVariable String zipCode){
        log.info("CityController -> méthode listCitiesByZipCode : entrée ");
        List<City> list = cityDao.findByZipCode(zipCode);

        for(City city :list){
            city.setDepartment(departmentDao.findByCode(city.getDepartmentCode()));
            city.getDepartment().setRegion(regionDao.findByCode(city.getDepartment().getRegionCode()));
        }

        log.info("CityController -> méthode listCitiesByZipCode : sortie ");
        return list;
    }
}
