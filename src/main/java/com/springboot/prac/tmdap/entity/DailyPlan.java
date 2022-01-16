package com.springboot.prac.tmdap.entity;

import javafx.concurrent.Task;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "T_TMD_DAILY_PLAN")
public class DailyPlan {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PLAN_DATE")
    private Date planDate;

    @Column(name = "TOTAL_HOURS")
    private Double totalHours;

    @Column(name = "OVER_WORK_HOURS")
    private Double overWorkHours;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "T_TMD_TASK_DAILY_PLAN",
    joinColumns = @JoinColumn(name = "DAILY_PLAN_ID", referencedColumnName = "ID"),
    inverseJoinColumns = @JoinColumn(name = "TASK_ID"))
    private Set<Tasks> taskSet = new HashSet<>();

    @OneToMany(fetch=FetchType.EAGER, mappedBy="dailyPlanId",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<TaskDailyPlan> taskDailyPlan;

}
