package com.xc.qrcode.web;

import com.xc.qrcode.util.QRCodeReadUtil;
import com.xc.qrcode.util.QRCodeWriteUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@RestController
public class QrController {


    //以base64形式返回,需要前端解析
    // 依靠前端进行解决，参考main.js文件
    @GetMapping("/getQrCodeOne")
    public ResponseEntity<String> getQrCodeBase64(@RequestParam("content") String content){
        String base64Str= QRCodeWriteUtil.createCodeToBase64(content);

        return StringUtils.hasLength(base64Str)?ResponseEntity.ok().body(base64Str):
                ResponseEntity.badRequest().body("二维码图片创建失败");
    }
    //直接输出到响应流
    // TODO 未实现图片附件下载
    @GetMapping("/getQrCodeTwo")
    public void getQrCode(@RequestParam("content") String content, HttpServletResponse response){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            QRCodeWriteUtil.createCodeToOutputStream(content,out);
            out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/resolveQrCode")
    public ResponseEntity<String> resolveQrCode(@RequestParam("img") MultipartFile file){
        try{
            String upFileName=file.getOriginalFilename();
            File tem=File.createTempFile("1234",
                    upFileName.substring(upFileName.lastIndexOf(".")));
            file.transferTo(tem);
            String  contentInQr=QRCodeReadUtil.parseQRCodeByFile(tem);
            deleteFile(tem);
            if(StringUtils.hasLength(contentInQr)){
                return ResponseEntity.ok().body(contentInQr);
            }else{
                return ResponseEntity.ok("请上传内容不为空的二维码");
            }
        }catch(IOException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("服务器端临时文件创建失败");
        }

    }

    /**
     * 文件删除
     * @param files
     */
    private void deleteFile(File... files) {
        Stream.of(files).forEach(f->{
            if(f.exists()){
                f.delete();
            }
        });
    }


}

