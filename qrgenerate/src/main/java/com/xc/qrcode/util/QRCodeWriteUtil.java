package com.xc.qrcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 *二维码、条形码生成类
 */
public class QRCodeWriteUtil {

    /**
     * CODE_WIDTH:二维码宽度，单位像素
     * CODE_HEIGHT:二维码高度，单位像素
     * FRONT_COLOR:二维码前景色，0x000000，黑色
     * BACKGROUND_COLOR:二维码背景色，0xFFFFFF，白色
     * 颜色使用16进制，可以使用其他颜色，但注意前景色和背景色应该存在大的对比
     */
    private static final int CODE_WIDTH=400;
    private static final int CODE_HEIGHT=400;
    private static final int FRONT_COLOR=0x000000;
    private static final int BACKGROUND_COLOR=0xFFFFFF;

    /**
     * 在web时，可使用这个，将二维码图片进行保存，同时记录下content与图片地址映射关系
     * 可以用来缓存二维码，但不推荐
     * 生成二维码图片，并保存到系统中
     * @param codeContent 二维码内容
     */
    public static void createCodeToFile(String codeContent){
        //获取classpath
        // Thread.currentThread().getContextClassLoader().getResource("/").getPath()
        try{
            //获取系统目录
            String filePathDir= FileSystemView.getFileSystemView()
                    .getHomeDirectory().getAbsolutePath();
            //随机生成png格式图片
            String filename= System.currentTimeMillis() + ".png";
            BufferedImage bufferedImage=getBufImgByContent(codeContent);
            /**javax.imageio.ImageIO java 扩展的图像IO
             * write(RenderedImage im,String formatName,File output)
             *      im：待写入的图像
             *      formatName：图像写入的格式
             *      output：写入的图像文件，文件不存在时会自动创建
             *
             * 即将保存的二维码图片文件*/
            File codeImgFile=new File(filePathDir,filename);
            ImageIO.write(bufferedImage,"png",codeImgFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码图片，并直接写入到响应输出流
     * @param content
     * @param outputStream
     */
    public static void createCodeToOutputStream(String content, OutputStream outputStream){
        try {
            BufferedImage bufferedImage = getBufImgByContent(content);
            ImageIO.write(bufferedImage, "png", outputStream);
            /**将生成的图片流转成base64的格式，然后返回给前端进行展示。
             //定义字节输出流，将bufferedImage写入
             ByteArrayOutputStream out = new ByteArrayOutputStream();
             ImageIO.write(bufferedImage, "png", out);
             //将输出流转换成base64
             String str64 = Base64.getEncoder().encodeToString(out.toByteArray());
             */
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //以base64的形式进行二维码图片返回
    public static String createCodeToBase64(String content){
        try {
            BufferedImage bufferedImage = getBufImgByContent(content);
              //将生成的图片流转成base64的格式，然后返回给前端进行展示。
             //定义字节输出流，将bufferedImage写入
             ByteArrayOutputStream out = new ByteArrayOutputStream();
             ImageIO.write(bufferedImage, "png", out);
             //将输出流转换成base64
             String str64 = Base64.getEncoder().encodeToString(out.toByteArray());
             return str64;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static BufferedImage getBufImgByContent(String content){
        //获取classpath
        // Thread.currentThread().getContextClassLoader().getResource("/").getPath()
        try{
            /**
             * com.google.zxing.EncodeHintType 编码提示类型、枚举类型
             * EncodingHintType.CHARACTER_SET 设置字符编码类型
             * EncodingHintType.ERROR_CORRECTION 设置误差校正
             * EncodingHintType.MARGIN：设置二维码边距、单位像素、值越小，二维码距离四周越近
             */
            Map<EncodeHintType,Object> encodeSet=new HashMap<>(8);
            encodeSet.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            encodeSet.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            encodeSet.put(EncodeHintType.MARGIN,1);
            /**
             * MultiFormatWriter:多格式写入，工工厂类，存在两个encode方法，用于写入二维码或者条形码
             *     encode(String contents,BarcideFormat format int width,int height,Map<EncodeHintType,?> hint)
             *                  BarcodeFormat枚举
             * BitMatrix：位(比特)矩阵或叫2D矩阵，也就是需要的二维码
             */

            MultiFormatWriter writer=new MultiFormatWriter();
            BitMatrix bitMatrix=writer.encode(content, BarcodeFormat.QR_CODE,
                    CODE_WIDTH,CODE_HEIGHT,encodeSet);

            /**java.awt.image.BufferedImage：具有图像数据的可访问缓冲图像，实现了 RenderedImage 接口
             * BitMatrix 的 get(int x, int y) 获取比特矩阵内容，指定位置有值，则返回true，将其设置为前景色，否则设置为背景色
             * BufferedImage 的 setRGB(int x, int y, int rgb) 方法设置图像像素
             *      x：像素位置的横坐标，即列
             *      y：像素位置的纵坐标，即行
             *      rgb：像素的值，采用 16 进制,如 0xFFFFFF 白色
             */
            BufferedImage bufferedImage= MatrixToImageWriter.toBufferedImage(bitMatrix);
            return bufferedImage;
    }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
/**
    public static void main(String[] args) {
        String content="111111";
        createCodeToFile(content);
    }
 */
}
