package com.xc.minioaccess.controller;

import com.xc.minioaccess.domain.R;
import com.xc.minioaccess.service.MinioService;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class UploadController {

    private final MinioService minioService;

    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "fileName",required = false) String name) {
        String fileName=name!=null?name:file.getOriginalFilename();

        //String url = minioService.getUrl(fileName, 7, TimeUnit.DAYS);
        return R.success(minioService.upload(file,fileName));
    }

    @GetMapping("/policy")
    public R policy(@RequestParam("fileName")String fileName) {
        Map policy = minioService.getPolicy(fileName, ZonedDateTime.now().plusMinutes(10));
        return R.success(policy);
    }

    @GetMapping("/uploadUrl")
    public R uploadUrl(@RequestParam("fileName") String fileName) {
        String url = minioService.getPolicyUrl(fileName, Method.PUT, 2, TimeUnit.MINUTES);
        return R.success(url);
    }

    @GetMapping("/url")
    public R getUrl(@RequestParam("fileName")String fileName) {
        String url = minioService.getUrl(fileName, 7, TimeUnit.DAYS);
        return R.success(url);
    }

}
