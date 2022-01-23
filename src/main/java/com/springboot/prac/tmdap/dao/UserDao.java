package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    List<User> findByUnitId(String unitId);

    @Query(value = "select distinct unit_id from t_sys_user where user_id not in ( select user_id from t_sys_user_role where role_id = 'ROLE_SUPERVISOR')", nativeQuery = true)
    List<String> findUnitIdExceptSuper();

}
