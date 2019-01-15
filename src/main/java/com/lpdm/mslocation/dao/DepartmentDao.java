package com.lpdm.mslocation.dao;

import com.lpdm.mslocation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer> {

    Department findById(int id);
    Department findByCode(String code);
}
