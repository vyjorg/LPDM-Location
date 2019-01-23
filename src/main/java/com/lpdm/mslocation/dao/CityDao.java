package com.lpdm.mslocation.dao;

import com.lpdm.mslocation.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {

    City findById(int id);
    List<City> findByZipCode(String zipCode);
    List<City> findAllByNameContainingIgnoreCase(String name);

}
