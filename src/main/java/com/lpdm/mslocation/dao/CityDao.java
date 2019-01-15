package com.lpdm.mslocation.dao;

import com.lpdm.mslocation.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {

    City findById(int id);
    City findByZipCode(String zipCode);

}
