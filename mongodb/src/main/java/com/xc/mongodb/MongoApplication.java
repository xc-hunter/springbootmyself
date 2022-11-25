package com.xc.mongodb;

import com.xc.mongodb.entiry.TestName;
import com.xc.mongodb.entiry.UserDao;
import com.xc.mongodb.entiry.UserDo;
import com.xc.mongodb.repository.ProfileRepository;
import com.xc.mongodb.repository.TestNameRepository;
import com.xc.mongodb.repository.UserDoRepository;
import com.xc.mongodb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class MongoApplication{
    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class,args);
    }

    private final ProfileRepository profileRepository;
    private final UserDoRepository userDoRepository;
    private final UserRepository userRepository;
    private final TestNameRepository testNameRepository;

    @PostConstruct
    public void insertData(){
        UserDao userDao=new UserDao();
        userDao.setUsername("dao");
        userDao.setPassword("dao123");
        userDao.setCreateTime(new Date());
        UserDo userDo=new UserDo();
        userDo.setUsername("do");
        userDo.setPassword("do456");
        userDo.setCreateTime(new Date());
        userDoRepository.insert(userDo);
        userRepository.insert(userDao);
        TestName testName=new TestName();
        testName.setTestName("testname");
        testName.setTestPass("testpass");
        testName.setTestLength(1);
        testNameRepository.save(testName);
        //for (int i = 0; i < 10; i++) {
        //    Profile profile=new Profile();
        //    profile.setGender(i%2==0?1:2);
        //    profile.setNockname("xc"+i);
        //    profile.setExet("xcinfo"+i);
        //    Map<String,Object> map=new HashMap<>();
        //    map.put("profess"+i,"pro"+i);
        //    map.put("normal"+i,"nor"+i);
        //    profile.setMessage(map);
        //    profileRepository.insert(profile);
        //}

    }
}
