package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskItemDao extends JpaRepository<TaskItem, String>, JpaSpecificationExecutor<TaskItem> {
}
