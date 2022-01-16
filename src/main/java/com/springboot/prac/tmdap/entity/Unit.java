package com.springboot.prac.tmdap.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_SYS_UNIT")
public class Unit {

    @Id
    @Column(name = "UNIT_ID", nullable = false)
    private String unitId;

    @Column(name = "UNIT_NAME", nullable = false)
    private String unitName;

    @Column(name = "SUPERIOR_UNIT_ID")
    private String superiorUnitId;

}
