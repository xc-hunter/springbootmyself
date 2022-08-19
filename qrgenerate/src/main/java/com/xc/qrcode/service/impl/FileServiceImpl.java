package com.xc.qrcode.service.impl;

import com.xc.qrcode.service.FileService;
import com.xc.qrcode.util.FileProperties;
import com.xc.qrcode.web.response.FileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    private final Path fileRestoreLocation;

    @Autowired
    public FileServiceImpl(FileProperties fileProperties){
        this.fileRestoreLocation= Paths.get(fileProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileRestoreLocation);
        }catch(Exception e){
            throw new FileException("存储文件目录创建失败",e);
        }
    }

    /**
     * 存储文件到系统
     * @param file
     * @return
     */
    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new FileException("文件名包含不合法路径序列"+fileName);
            }
            Path targrtLocation=this.fileRestoreLocation.resolve(fileName);
            Files.copy(file.getInputStream(),targrtLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch(IOException ex){
            throw new FileException("无法保存文件"+fileName+"--请再次尝试");
        }
    }

    /**
     * 加载文件
     * @param fileName
     * @return
     */
    @Override
    public Resource loadFileAsResource(String fileName) {
        try{
            Path filePath=this.fileRestoreLocation.resolve(fileName).normalize();
            Resource resource=new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new FileException("文件无法找到"+fileName);
            }
        }catch(MalformedURLException ex){
            throw new FileException("文件无法找到"+fileName,ex);
        }
    }
}
