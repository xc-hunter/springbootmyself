package com.xc.mongodb;

import com.xc.mongodb.entiry.Profile;
import com.xc.mongodb.entiry.UserDao;
import com.xc.mongodb.entiry.UserDo;
import com.xc.mongodb.repository.ProfileRepository;
import com.xc.mongodb.repository.UserDoRepository;
import com.xc.mongodb.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class Test {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDoRepository userDoRepository;
    @Autowired
    private ProfileRepository profileRepository;
    //测试UserDao和UserDo存储到同一个集合中
    @org.junit.Test
    public void testInsert(){
        UserDao userDao=new UserDao();
        userDao.setUsername("userDao");
        userDao.setPassword("password");
        userDao.setCreateTime(new Date());
        userRepository.insert(userDao);
    }
    @org.junit.Test
    public void testInsertDo(){
        UserDo userDo=new UserDo();
        userDo.setUsername("userDo");
        userDo.setPassword("password");
        userDo.setCreateTime(new Date());
        userDoRepository.insert(userDo);
    }
    @org.junit.Test
    public void testInsertP(){
        Profile p=new Profile();
        p.setNockname("xie");
        Map<String,Object> map=new HashMap<>();
        map.put("1","one");
        map.put("2",2);
        p.setMessage(map);
        //p.setGender(1);
        //p.setExet("exetension");
        profileRepository.insert(p);
    }

}
