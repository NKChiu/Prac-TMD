package com.springboot.prac.tmdap.dao;


import com.springboot.prac.tmdap.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UnitDao extends JpaRepository<Unit, String>, JpaSpecificationExecutor<Unit> {

}
