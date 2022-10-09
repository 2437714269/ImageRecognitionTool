package com.acer.service;

import com.acer.common.ApiRestResponse;
import com.acer.model.Picture;
import com.acer.utils.ThumbnailsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 需求分析：
 * 功能1：缩放图片大小（0.1~1之间），并根据fileSize调整图片大小（KB）
 * 功能2：
 * @author acer
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PictureService {


    /**
     * 图片的默认配置默认配置
     */
    private final Picture picture;


    /**
     * 用户桌面路径file
     */
    private final String localhostPathFile;

    {
        // 获取用户桌面路径
        localhostPathFile =
                FileSystemView.getFileSystemView().getHomeDirectory().getAbsoluteFile().getAbsolutePath()+"/ProcessingCompleted";
    }



    /**
     * 图片缩放，等比例缩放，指定图片大小缩放
     * @author acer
     * @date 11:17 2022/10/8
     * @param file 要修改图片
     * @param height 要修改图片高
     * @param width 要修改图片宽
     * @param desFileSize 要修改图片大小（小于fileSize）指定图片大小,单位kb
     * @param accuracy 等比例缩放（优先级比指定大小低）
     * @return com.acer.common.ApiRestResponse
    **/
    public ApiRestResponse zoom(List<MultipartFile> file,Integer height,Integer width,Long desFileSize,Double accuracy) throws IOException {
        if (file == null){
            return ApiRestResponse.error(400,"file参数不能为空");
        }
        // 判断accuracy是否为空，非空：用前端传输的，空：用默认配置
        if (accuracy == null || accuracy >=1){
            accuracy = picture.getAccuracy();
        }
        // 判断宽高是否为空height、width，非空：用前端传的，空：用默认配置
        if (height != null || width != null && desFileSize != null){
            height = picture.getHeight();
            width = picture.getWidth();
            accuracy = 0.0;
            return this.uploadFile(file,height,width,desFileSize,accuracy);
        }
        // 判断desFileSize是否为空，非空：用前端传输的，空：用默认配置
        if (desFileSize == null || desFileSize >=2000){
            return ApiRestResponse.error(400,"无缩放大小或要缩放大小超过2000kb");
        }
        // 调用uploadFile方法进行图片缩放
        return this.uploadFile(file,height,width,desFileSize,accuracy);
    }




    /**
     * 图片缩放，等比例缩放，指定图片大小缩放（操作）
     * @author acer
     * @date 11:24 2022/9/29
     * @param file 要修改图片
     * @param height 要修改图片高
     * @param width 要修改图片宽
     * @param desFileSize 要修改图片大小（小于fileSize）
     * @param accuracy 等比例缩放（优先级比指定大小低）
     * @return java.lang.String
    **/
    private ApiRestResponse uploadFile(List<MultipartFile> file, Integer height, Integer width, Long desFileSize, Double accuracy) throws IOException {

        // 1.判断file是否为空
        if (file == null || file.size() == 0){
            return ApiRestResponse.error(400,"上传文件不能为空");
        }
        // 在桌面路径下，创建一个文件夹（ProcessingCompleted/uploadFile），将处理好的文件放到里面
        File localhostPathFileUploadFile  = new File(localhostPathFile+"/uploadFile");
        localhostPathFileUploadFile.mkdirs();
        // 2.判断宽，高是否为空,因为指定图片大小的优先级比等比例缩放的优先级要高，所有不用判断proportion，注意：指定大小缩放，不能与fileSize文件指定大小同时存在
        if (height !=null && width != null){
            // 判断height和width是否为合法参数
            if (height >0 && width >0) {
                // 遍历file文件
                for (MultipartFile multipartFile : file) {
                    // 获取每个文件的名字
                    String fileName = multipartFile.getOriginalFilename();
                    // 拼接目标路径
                    File outPath = new File(localhostPathFileUploadFile.getAbsolutePath()+ "/" + fileName);
                    // 调用ThumbnailsUtils中的zoom方法进行修改图片
                    ThumbnailsUtils.zoom(multipartFile.getInputStream(), outPath, width, height);
                }
            }
            return ApiRestResponse.error(400,"宽/高参数有问题");
        }
        // 存储文件名返回前端
        ArrayList<String> fileName = new ArrayList<>();
        // 3.等比例缩放，递归缩放大小，同时判断大小是否大于fileSize
        if(accuracy != null && desFileSize != null){
            // 文件个数标记
            int sign = file.size();
            // 遍历file对象
            for (MultipartFile multipartFile : file) {
                // 输出流
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localhostPathFileUploadFile.getAbsolutePath()+"/"+multipartFile.getOriginalFilename()));
                bos.write(commpressPicCycle(multipartFile.getBytes(),desFileSize,accuracy));
                bos.flush();
                bos.close();
                log.info(multipartFile.getOriginalFilename()+":文件已完成,文件个数："+ sign-- +"个");
                fileName.add(multipartFile.getOriginalFilename());
            }

        }

        return ApiRestResponse.success(200,"成功",fileName);
    }



    /**
     * 调整图片大小
     * @author acer
     * @date 15:56 2022/9/29
     * @param bytes 原图片字节数组
     * @param desFileSize 指定图片大小,单位 kb
     * @param accuracy 精度,递归压缩的比率,建议小于0.9
    **/
    private byte[] commpressPicCycle(byte[] bytes, long desFileSize, Double accuracy) throws IOException {

        // 判断图片大小是否小于desFileSize
        long fileSize = bytes.length;
        if ((desFileSize * 1024) >= fileSize){
            return bytes;
        }
        // 获取原图片宽高
        BufferedImage bim = ImageIO.read(new ByteArrayInputStream(bytes));
        // 宽
        int imgWidth = bim.getWidth();
        // 高
        int imgHeight = bim.getHeight();

        //计算宽高
        int desWidth = new BigDecimal(imgWidth).multiply(new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(imgHeight).multiply(new BigDecimal(accuracy)).intValue();
        // 字节输出流（写入到内存）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 判断accuracy是否为空，非空：用前端设置的，空：用默认的
        ThumbnailsUtils.zoom(bytes,desWidth,desHeight,accuracy,baos);
        //如果不满足要求,递归直至满足要求
        return commpressPicCycle(baos.toByteArray(),desFileSize,accuracy);
    }



    /**
     * 修改图片类型:jpg-png-jpeg-gif
     * @author acer
     * @date 15:46 2022/10/8
     * @param file 图片集合
     * @param forMat 要修改图片类型
     * @return com.acer.common.ApiRestResponse
    **/
    public ApiRestResponse picturesRotating(List<MultipartFile> file,String forMat) throws IOException {
        if (!"jpg".equals(forMat) && !"jpeg".equals(forMat) && !"git".equals(forMat) && !"png".equals(forMat)){
            return ApiRestResponse.error(400,"forMat类型不正确：请在{jpg、jpeg、git、png}中选择");
        }
        ArrayList<String> pictureName = new ArrayList<>();
        // 图片个数
        int number = file.size();
        for (MultipartFile multipartFile : file) {
            // 如果要操作的图片类型与forMat一样，直接跳过，判断下张图片
            String pictureType = multipartFile.getOriginalFilename().split("\\.")[1];
            //判断当前类型，是否跟要修改的forMat的类型一样，一样直接跳过本次循环，否则修改
            if (pictureType.equals(forMat)){
                //跳过本次循环
                continue;
            }
            // 创建modifyPictureType的File
            File pathFile = new File(localhostPathFile+"/modifyPictureType");
            //创建文件夹
            pathFile.mkdirs();
            // 在桌面路径下，创建一个文件夹（ProcessingCompleted/modifyPictureType），将处理好的文件放到里面
            String newPhoto = localhostPathFile+"/modifyPictureType/"+ multipartFile.getOriginalFilename().split("\\.")[0]+"."+forMat;
            // 获取图片宽高
            BufferedImage bim = ImageIO.read(multipartFile.getInputStream());
            // 宽
            int imgWidth = bim.getWidth();
            // 高
            int imgHeight = bim.getHeight();
            Thumbnails.of(multipartFile.getInputStream()).size(imgWidth, imgHeight).outputFormat(forMat).toFile(newPhoto);
            log.info(multipartFile.getOriginalFilename()+"-图片个数：{}",number--);
            pictureName.add(multipartFile.getOriginalFilename());
        }
        return ApiRestResponse.success(200,"成功",pictureName);
    }



























}
