package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    List<User> findByUnitId(String unitId);
}
