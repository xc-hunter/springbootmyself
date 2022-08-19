package com.xc.datajpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String userName;
    @Column
    private String password;
    //cascade，添加级联，保证在插入数据时，保存Parent时保存Child
    @OneToOne(cascade = CascadeType.ALL)
    private Role role;
}
