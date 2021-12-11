package com.springboot.prac.tmdap.dao;

import com.springboot.prac.tmdap.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskDao extends JpaRepository<Tasks, String>, JpaSpecificationExecutor<Tasks> {

    List<Tasks> findByTaskNameLike(String taskName);
    List<Tasks> findByTaskNameContainingIgnoreCase(String taskName);
    @Query(value = "from Tasks t where lower(t.taskName) like lower(concat('%', :taskName, '%') ) ")
    List<Tasks> queryByTaskNameNoCase(@Param("taskName") String taskName);

    List<Tasks> findByTaskMappingIdIn(List<String> taskMappingId);
}
