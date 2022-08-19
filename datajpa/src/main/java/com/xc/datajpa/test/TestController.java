package com.xc.datajpa.test;

import com.xc.datajpa.dao.PermissionDao;
import com.xc.datajpa.dao.RoleDao;
import com.xc.datajpa.dao.UserDao;
import com.xc.datajpa.entity.PartAttr;
import com.xc.datajpa.entity.Role;
import com.xc.datajpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/xc")
public class TestController {
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionDao permissionDao;
    @GetMapping(value = {"/","/{userid}"})
    public List<User> getUser(@PathVariable(name = "userid",required = false)Integer id){
        List result=new ArrayList<User>();
        if(!Objects.isNull(id)){
            result.add(userDao.findById(id).get());
        }else{
            result=userDao.findAll();
        }
        return result;
    }

    /**
     * 用于测试获取返回一个表的部分表属性，步骤为
     * 1、定义一个接口，接口中定义属性get方法，属性名对应与实体类
     * 2、在Repository中定义相应方法
     * tips：在接口中可进行属性的处理，可使用@Value注解方法或者默认方法，详情参见PartAttr接口
     * @param id
     * @return
     */
    @GetMapping(value = {"part/","part/{userid}"})
    public List<PartAttr> getPart(@PathVariable(name = "userid",required = false)Integer id){
        List result=new ArrayList<PartAttr>();
        if(!Objects.isNull(id)){
            result=userDao.findPById(id);
        }else{
            result=userDao.findAll();
        }
        return result;
    }
    //保存数据，id不用设置，
    //执行语句如下
    //insert into t_role (rolename) values (?)
    //insert into t_user (password, role_id, username) values (?, ?, ?)
    @GetMapping("/save")
    public String save(){
        Role r=new Role();
        r.setRoleName("other");
        User u=new User();
        u.setUserName("xc4");
        u.setPassword("123456");
        u.setRole(r);
        userDao.save(u);
        return "保存成功";
    }
    //修改数据
    //修改数据测试，第一种情况，设置数据库中不存在的role数据，在保存user时，数据库会updaterole表
    //修改数据：先查询获取结果，修改后保存。
    @GetMapping("/update")
    public User update(){
        User u=userDao.getById(8);
        u.setId(4);
        User newu=userDao.save(u);
        return newu;
    }
}
