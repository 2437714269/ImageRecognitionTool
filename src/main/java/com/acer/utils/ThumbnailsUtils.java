package com.acer.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Thumbnails的方法
 * 1.按照比例进行缩放
 * 2.等比例缩放
 * 3.指定大小进行缩放
 * 4.不按照比例，指定大小进行缩放
 * @author acer
 */
@Slf4j
public class ThumbnailsUtils {


    /**
     * 按照比例进行缩放
     * @author acer
     * @date 11:29 2022/9/15
     * @param inputPath 原路径
     * @param outPath 目标路径
     * @param proportion 缩放大小
    **/
    public static void zoom(File inputPath,File outPath,double proportion) throws IOException {
        // scale(比例)，放大和缩小
        Thumbnails.of(inputPath).scale(proportion).toFile(outPath);
    }
    /**
     * 指定图片大小缩放（参数绝对路径方式）
     * @author acer
     * @date 11:29 2022/9/15
     * @param inputPath 原路径
     * @param outPath 目标路径
     * @param desHeight 图片宽度
     * @param desWidth 图片高度
    **/
    public static void zoom(String inputPath,String outPath,int desWidth,int desHeight) throws IOException {
        Thumbnails.of(inputPath).size(desWidth,desHeight).toFile(outPath);
    }

    /***
     * 指定图片大小缩放（参数file对象方式）
     * @author acer
     * @date 13:03 2022/9/29
     * @param inputPath 原路径
     * @param outPath 目标路径
     * @param width 图片宽度
     * @param height 图片高度
    **/
    public static void zoom(InputStream inputPath, File outPath, int width, int height) throws IOException {
        Thumbnails.of(inputPath).size(width,height).toFile(outPath);
    }
    /**
     * 字节修改图片
     * @author acer
     * @date 11:39 2022/10/8
     * @param inputStreams 原图片字节数组
     * @param desWidth 图片高度
     * @param desHeight 图片宽度
     * @param accuracy 要缩放的大小
     * @param baos 字节输出流（写入到内存）
    **/
    public static void zoom(byte[] inputStreams,int desWidth, int desHeight,Double accuracy,ByteArrayOutputStream baos) throws IOException {
        Thumbnails.of(new ByteArrayInputStream(inputStreams)).size(desWidth, desHeight).outputQuality(accuracy).toOutputStream(baos);
    }


    /**
     * 指定大小进行缩放
     * @author acer
     * @date 10:12 2022/10/9
    **/
    private void zoom() throws IOException {
        /*
         * size(width,height) 若图片宽比200小，高比300小，不变
         * 若图片宽比200小，高比300大，高缩小到300，图片比例不变 若图片宽比200大，高比300小，宽缩小到200，图片比例不变
         * 若图片宽比200大，高比300大，图片按比例缩小，宽为200或高为300
         */
        Thumbnails.of("images/test.jpg").size(200, 300).toFile("C:/image_200x300.jpg");
        Thumbnails.of("images/test.jpg").size(2560, 2048).toFile("C:/image_2560x2048.jpg");
    }



    /**
     * 不按照比例，指定大小进行缩放
     * @author acer
     * @date 10:12 2022/10/9
    **/
    private void test3() throws IOException {
        // keepAspectRatio(false) 默认是按照比例缩放的
        Thumbnails.of("images/test.jpg").size(120, 120).keepAspectRatio(false).toFile("C:/image_120x120.jpg");
    }

    /**
     * 旋转
     * @author acer
     * @date 10:13 2022/10/9
    **/
    private void test4() throws IOException {
        //rotate(角度),正数：顺时针 负数：逆时针
        Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(90).toFile("C:/image+90.jpg");
        Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(-90).toFile("C:/iamge-90.jpg");
    }

    /**
     * 水印
     * @author acer
     * @date 10:13 2022/10/9
    **/
    private void test5() throws IOException {
        // watermark(位置，水印图，透明度)
        Thumbnails.of("images/test.jpg").size(1280, 1024).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("images/watermark.png")), 0.5f)
                .outputQuality(0.8f).toFile("C:/image_watermark_bottom_right.jpg");
        Thumbnails.of("images/test.jpg").size(1280, 1024).watermark(Positions.CENTER, ImageIO.read(new File("images/watermark.png")), 0.5f)
                .outputQuality(0.8f).toFile("C:/image_watermark_center.jpg");
    }

    /**
     * 裁剪
     *
     * @throws IOException
     */
    private void test6() throws IOException {
        // 图片中心400*400的区域
        Thumbnails.of("images/test.jpg").sourceRegion(Positions.CENTER, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile("C:/image_region_center.jpg");
        // 图片右下400*400的区域
        Thumbnails.of("images/test.jpg").sourceRegion(Positions.BOTTOM_RIGHT, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile("C:/image_region_bootom_right.jpg");
        // 指定坐标
        Thumbnails.of("images/test.jpg").sourceRegion(600, 500, 400, 400).size(200, 200).keepAspectRatio(false).toFile("C:/image_region_coord.jpg");
    }

    /**
     * 转化图像格式
     * @author acer
     * @date 10:14 2022/10/9
    **/
    private void test7() throws IOException {
        // outputFormat(图像格式)
        Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("png").toFile("C:/image_1280x1024.png");
        Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("gif").toFile("C:/image_1280x1024.gif");
    }

    /**
     * 输出到OutputStream
     * @author acer
     * @date 10:14 2022/10/9
    **/
    private void test8() throws IOException {
        // toOutputStream(流对象)
        OutputStream os = new FileOutputStream("C:/image_1280x1024_OutputStream.png");
        Thumbnails.of("images/test.jpg").size(1280, 1024).toOutputStream(os);
    }

    /**
     * 输出到BufferedImage
     * @author acer
     * @date 10:15 2022/10/9
    **/
    private void test9() throws IOException {
        // asBufferedImage() 返回BufferedImage
        BufferedImage thumbnail = Thumbnails.of("images/test.jpg").size(1280, 1024).asBufferedImage();
        ImageIO.write(thumbnail, "jpg", new File("C:/image_1280x1024_BufferedImage.jpg"));
    }
}
