package com.xc.qrcode.util;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取二维码内容
 */
public class QRCodeReadUtil {

    /**
     * 解析二维码内容(文件)
     * @param file
     * @return
     * @throws Exception
     */
    public static String parseQRCodeByFile(File file)throws IOException{
        BufferedImage bufferedImage= ImageIO.read(file);
        return parseQRCode(bufferedImage);


    }

    /**
     * 解析二维码内容(网络链接)
     * @param url
     * @return
     * @throws IOException
     */
    public static String parseQRCodeByUrl(URL url) throws IOException{
        BufferedImage bufferedImage=ImageIO.read(url);
        return parseQRCode(bufferedImage);
    }

    /**
     * 解析二维码内容
     * @param bufferedImage
     * @return
     */
    private static String parseQRCode(BufferedImage bufferedImage) {
        try{
            /**
             * com.google.zxing.client.j2se.BufferedImageLuminanceSource:缓冲图像亮度源
             * 将java.awt.iamge.BufferedImage 转为zxing的缓冲图像亮度源
             * HybridBinarizer 用于读取二维码图像数据，BinaryBitmap 二进制位图
             */
            LuminanceSource source=new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap=new BinaryBitmap(new HybridBinarizer(source));
            Map<DecodeHintType,Object> hints=new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET,"UTF-8");
            /**
             * 如果图片不是二维码图片，则 decode 抛异常：com.google.zxing.NotFoundException
             * MultiFormatWriter 的 encode 用于对内容进行编码成 2D 矩阵
             * MultiFormatReader 的 decode 用于读取二进制位图数据
             */
            Result result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
