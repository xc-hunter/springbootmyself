package com.xc.datajpa.dao;

import com.xc.datajpa.entity.PartAttr;
import com.xc.datajpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**实验过程中List<PartAttr> findPAll()，
 * No property findPAll found for type User
 * 无法使用，可能原因是无法根据方法名构建
 * findPById成功，其可以根据方法名和结果类型构建query
 */

/**
 * @Query专题
 * 参数绑定在@Query注解时有用，参数绑定两个途径
 * 1、可通过 ？num获取方法参数，存在问题是方法参数重构时，语句也需要修改
 * 2、在方参上使用@Param(paraName),在语句中通过 :paraName获取到值，方法重构时不受影响
 * @Query中可使用受限制的SpEL，如存在一个属性entityName可用于获取表名
 * select name from #{#(entityName)} where id=?1
 * 使用entityName好处
 * 1、sql语句中不显式出表名
 * 2、后续entity对应的数据库表更改时，可直接修改@Entity的name属性，不需要修改sql语句
 * 3、泛型，特殊用途，
 */
public interface  UserDao extends JpaRepository<User,Integer> {
    //获取部分属性的方法声明
    List<PartAttr> findPById(Integer id);

}
/**
 * 派生的delete方法执行过程为：先查询出数据，在逐个删除（crudRepository.delete(Iterable<T> t)）
 */