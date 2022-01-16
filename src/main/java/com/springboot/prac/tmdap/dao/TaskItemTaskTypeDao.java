package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.TaskItemTaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskItemTaskTypeDao extends JpaRepository<TaskItemTaskType, String>, JpaSpecificationExecutor<TaskItemTaskType> {
    List<TaskItemTaskType> findByTaskItemId(String taskItemId);
    List<TaskItemTaskType> findByTaskTypeId(String taskTypeId);

}
