package com.springboot.prac.tmdap.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

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

}
