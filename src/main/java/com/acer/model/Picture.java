package com.acer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 图片描述
 * @author acer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
@ConfigurationProperties(prefix = "picture")
public class Picture {

    /**
     * 照片高度
     * 默认1280
     */
    @Value("${picture.height}")
    private Integer height;
    /**
     * 照片宽度
     * 默认1024
     */
    @Value("${picture.width}")
    private Integer width;

    /**
     * 如果文件大于fileSize需要调整图片大小
     * 默认100kb
     */
    @Value("${picture.fileSize}")
    private Long fileSize;

    /**
     * 源图片路径
     */
    private String inputPath;
    /**
     * 输出图片路径
     */
    private String outPath;


    /**
     * 图片缩放的比例
     */
    @Value("${picture.proportion}")
    private Double accuracy;
}
