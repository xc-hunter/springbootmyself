package com.xc.datajpa.all;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 不启动服务，但配备了整个上下文
//@SpringBootTest(classes = DatajpaApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestByMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMvc()throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/xc/")).andDo(print()).
        andExpect(status().isOk()).
        andExpect(content().string(containsString("xc")));
    }
}
