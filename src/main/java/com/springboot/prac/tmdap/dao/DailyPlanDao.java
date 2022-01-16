package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.DailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DailyPlanDao extends JpaRepository<DailyPlan, String>, JpaSpecificationExecutor<DailyPlan> {

    @Query(value="from DailyPlan dp where dp.planDate >= :startDate and dp.planDate <= :endDate")
    List<DailyPlan> getDailyPlansByPlanDate(@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

}
