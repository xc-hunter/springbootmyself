package com.xc.datajpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "t_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "permissionname")
    private String permissionName;
    @ManyToMany
    @JoinColumn(name = "role_id")
    private List<Role> roles;
}
