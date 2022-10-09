package com.acer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author acer
 */
@SpringBootApplication
@Slf4j
public class SpringbootApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringbootApplication.class, args);
            log.info("项目启动成功...");
        }catch (Exception e){
            log.info("test服务启动失败！",e);
        }
    }

}
