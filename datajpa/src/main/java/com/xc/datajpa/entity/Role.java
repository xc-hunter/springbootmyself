package com.xc.datajpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "rolename")
    private String roleName;
}
