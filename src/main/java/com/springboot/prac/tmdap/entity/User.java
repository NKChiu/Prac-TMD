package com.springboot.prac.tmdap.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_SYS_USER")
public class User {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "UNIT_ID")
    private String unitId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UNIT_ID", insertable = false, updatable = false)
    private Unit unit;

}
