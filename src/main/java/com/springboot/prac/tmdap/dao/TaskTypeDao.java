package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskTypeDao extends JpaRepository<TaskType, String>, JpaSpecificationExecutor<TaskType> {
}
