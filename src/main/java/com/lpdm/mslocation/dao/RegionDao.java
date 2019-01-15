package com.lpdm.mslocation.dao;

import com.lpdm.mslocation.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionDao extends JpaRepository<Region, Integer> {

    Region findById(int id);
    Region findByCode(String code);
}
