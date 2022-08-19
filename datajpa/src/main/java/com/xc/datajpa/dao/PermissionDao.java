package com.xc.datajpa.dao;

import com.xc.datajpa.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends JpaRepository<Permission,Integer> {

}
