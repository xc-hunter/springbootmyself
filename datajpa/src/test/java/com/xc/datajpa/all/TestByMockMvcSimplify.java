package com.xc.datajpa.all;

import com.xc.datajpa.dao.PermissionDao;
import com.xc.datajpa.dao.RoleDao;
import com.xc.datajpa.dao.UserDao;
import com.xc.datajpa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 存在限制，@WebMvcTest只是会实例化web层，而不是整个上下文，即只会进行@Controller的实例
// 需要保证控制器没有其他依赖，才可以进行测试
// Controller需要其他Bean时，使用@MockBean注入所有的依赖
@WebMvcTest
//在测试类的层次低于主启动类时不需要指定启动类位置
public class TestByMockMvcSimplify {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDao userDao;

    @MockBean
    private RoleDao roleDao;

    @MockBean
    private PermissionDao permissionDao;

    @Test
    public void testMvcSimplify()throws Exception{
        List<User> mockResult=new ArrayList<>();
        User a=new User();
        a.setId(1);
        a.setUserName("xc");
        mockResult.add(a);
        // 对于mock的bean进行设置
        given(userDao.findAll()).willReturn(mockResult);
        this.mockMvc.perform(get("/xc/")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("xc")));
    }
}
