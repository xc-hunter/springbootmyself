package com.xc.datajpa.all;

import com.xc.datajpa.test.TestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test:
 * [Test the web Layer](https://spring.io/guides/gs/testing-web/)
 * 使用Junit测试,MockMvc测试web层
 */
@SpringBootTest
public class TestInject {

    @Autowired
    private TestController testController;

    @org.junit.jupiter.api.Test
    public void contextLoad(){
        //断言
        assertThat(testController).isNotNull();
    }
}
