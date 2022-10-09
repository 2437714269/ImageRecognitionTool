package com.acer.controller;

import com.acer.common.ApiRestResponse;
import com.acer.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author acer
 *
 * zoom:
 *      图片缩放，
 *      等比例缩放（百分比），
 *      指定大小进行缩放，
 * rotate：
 *      旋转，正数：顺时针 负数：逆时针
 * watermark：
 *      水印，
 * formatConversion
 *      转化图像格式，
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class PictureController {


    private final PictureService pictureService;

    /**
     * 图片缩放，等比例缩放，指定图片大小缩放
     * @author acer
     * @date 11:24 2022/9/29
     * @param file 要修改图片
     * @param height 要修改图片高
     * @param width 要修改图片宽
     * @param desFileSize 要修改图片大小（小于fileSize）
     * @param accuracy 等比例缩放（优先级比指定大小低）
     * @return com.acer.common.ApiRestResponse
    **/
    @PostMapping("/uploadMinIO")
    public ApiRestResponse zoom(@RequestParam(name = "file", required = false) List<MultipartFile> file, Integer height, Integer width, Long desFileSize, Double accuracy) throws IOException {
        return pictureService.zoom(file,height,width,desFileSize,accuracy);
    }

    /**
     * 修改图片类型
     * @author acer
     * @date 9:16 2022/10/9
     * @param file 要修改的图片
     * @param forMat 要修改图片的类型
     * @return com.acer.common.ApiRestResponse
    **/
    @PostMapping("/formatConversion")
    public ApiRestResponse formatConversion(@RequestParam(name = "file", required = false) List<MultipartFile> file,String forMat) throws IOException {
        return pictureService.picturesRotating(file,forMat);
    }









}
