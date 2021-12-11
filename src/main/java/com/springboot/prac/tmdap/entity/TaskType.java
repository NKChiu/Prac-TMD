package com.springboot.prac.tmdap.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_TMD_TASK_TYPE")
public class TaskType {
    @Id
    @Column(name = "TASK_TYPE_ID")
    private String taskTypeId;

    @Column(name = "TASK_TYPE_NAME")
    private String taskTypeName;


}
