package com.lpdm.mslocation.dao;

import com.lpdm.mslocation.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, Integer> {

    Address findById(int id);
}
