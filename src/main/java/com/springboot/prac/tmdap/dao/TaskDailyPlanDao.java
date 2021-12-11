package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.TaskDailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskDailyPlanDao extends JpaRepository<TaskDailyPlan, String>, JpaSpecificationExecutor<TaskDailyPlan> {
    List<TaskDailyPlan> findByDailyPlanIdIn(List<String> id);
    List<TaskDailyPlan> findByTaskIdAndDailyPlanId(String taskId, String planId);
    List<TaskDailyPlan> findByTaskId(String taskId);
    List<TaskDailyPlan> findByTaskIdIn(List<String> taskId);
}
