package com.xc.minioaccess;

import com.xc.minioaccess.domain.R;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
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
import java.io.FileOutputStream;
import java.io.InputStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestUpload {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MinioClient minioClient;

    @Test
    public void test(){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String,Object> param=new LinkedMultiValueMap<>();
        param.add("file", new FileSystemResource(new File("C:\\Users\\klm01\\Desktop\\1.txt")));
        param.add("fileName", "custom.txt");
        param.add("directory", "terminal/upgrade");
        HttpEntity<MultiValueMap<String,Object>> request=new HttpEntity<>(param,headers);

        R r=restTemplate.postForObject("http://localhost:8087/upload",request, R.class);
        System.out.println(r.getData());
    }

    @Test
    public void delete() throws Exception{
        minioClient.removeObject(RemoveObjectArgs.builder().bucket("xctest").object("config-sharding.yaml").build());
    }

    @Test
    public void get() throws Exception{
        InputStream getObjectResponse=minioClient.getObject(GetObjectArgs.builder().bucket("xctest").object("terminal/upgrade/custom.txt").build());
        FileOutputStream fileOutputStream=new FileOutputStream(new File("C:\\Users\\klm01\\Desktop\\2.txt"));
        int len=0;
        byte[] bytes=new byte[1024];
        System.out.println(getObjectResponse.available());
        while((len= getObjectResponse.read(bytes))>0){
            System.out.println(len);
            fileOutputStream.write(bytes,0,len);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        getObjectResponse.close();
    }
}
