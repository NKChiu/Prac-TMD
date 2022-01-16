package com.springboot.prac.tmdap.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_TMD_TASK_ITEM_TASK_TYPE")
public class TaskItemTaskType {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "TASK_ITEM_ID")
    private String taskItemId;

    @Column(name = "TASK_TYPE_ID")
    private String taskTypeId;

}
