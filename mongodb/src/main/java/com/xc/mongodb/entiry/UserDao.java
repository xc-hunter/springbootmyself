package com.xc.mongodb.entiry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "user")
public class UserDao {
    //使用注解方法时，此处不可使用包装类，因为在子定义的注解那里进行了判断
    // 如果使用了包装类，值为null，判断失败，就不会赋自定义的自增id
    // 且mongodb默认的自增id为12字节，需要使用Stirng类型才可采用默认自动生成
    @Id
    @AutoIncId
    private long id;

    //@Indexed
    private String username;

    private String password;

    private Date createTime;


}
 //db.user.find()
 //        [
 //        {
 //        _id: Long("1"),
 //        username: 'do',
 //        password: 'do456',
 //        createTime: ISODate("2022-11-25T03:34:22.567Z"),
 //        _class: 'com.xc.mongodb.entiry.UserDo'
 //        },
 //        {
 //        _id: Long("2"),
 //        username: 'dao',
 //        password: 'dao123',
 //        createTime: ISODate("2022-11-25T03:34:22.567Z"),
 //        _class: 'com.xc.mongodb.entiry.UserDao'
 //        }
