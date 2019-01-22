package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.dao.CityDao;
import com.lpdm.mslocation.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    @Autowired
    private CityDao cityDao;

    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> listCities(){
        List<City> list = cityDao.findAll();
        return list;
    }
}
