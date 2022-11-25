package com.xc.minioaccess.service;

import io.minio.http.Method;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface MinioService {

    String pureUpload(MultipartFile file, String fileName);

    String uploadGetPublicUrl(MultipartFile file, String fileName);

    String getUrl(String fileName, int i, TimeUnit days);

    Map getPolicy(String fileName, ZonedDateTime plusMinutes);

    String getPolicyUrl(String fileName, Method put, int i, TimeUnit minutes);
}
