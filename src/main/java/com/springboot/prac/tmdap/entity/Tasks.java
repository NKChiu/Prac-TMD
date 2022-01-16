package com.springboot.prac.tmdap.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "T_TMD_TASKS")
public class Tasks {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "ASSINGEE_ID")
    private String assigneeId;

    @Column(name = "TASK_MAPPING_ID")
    private String taskMappingId;

    @Column(name = "CREATE_DATE")
    private Date createDate;

}
