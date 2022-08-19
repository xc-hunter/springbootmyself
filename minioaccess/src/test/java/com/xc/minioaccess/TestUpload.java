package com.xc.minioaccess;

import com.xc.minioaccess.domain.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestUpload {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void test(){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String,Object> param=new LinkedMultiValueMap<>();
        param.add("file", new FileSystemResource(new File("C:\\Users\\klm01\\Desktop\\1.png")));
        HttpEntity<MultiValueMap<String,Object>> request=new HttpEntity<>(param,headers);

        R r=restTemplate.postForObject("http://localhost:8087/upload",request, R.class);
        System.out.println(r.getData());
    }
}
