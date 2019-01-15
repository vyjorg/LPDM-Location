package com.lpdm.mslocation.dao;

import com.lpdm.mslocation.entity.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressDao extends JpaRepository<Adress, Integer> {

    Adress findById(int id);
}
