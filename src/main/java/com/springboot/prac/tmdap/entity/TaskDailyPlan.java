package com.springboot.prac.tmdap.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_TMD_TASK_DAILY_PLAN")
public class TaskDailyPlan {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "DAILY_PLAN_ID")
    private String dailyPlanId;

    @Column(name = "WORKING_HOURS")
    private Double workingHours;

}
