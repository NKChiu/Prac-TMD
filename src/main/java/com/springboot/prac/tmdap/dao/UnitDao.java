package com.springboot.prac.tmdap.dao;


import com.springboot.prac.tmdap.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UnitDao extends JpaRepository<Unit, String>, JpaSpecificationExecutor<Unit> {

    List<Unit> findByUnitIdIn(List<String> unitIds);
}
