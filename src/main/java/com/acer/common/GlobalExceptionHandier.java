package com.acer.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author acer
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandier{


    /**
     * 全部异常捕获
     * @author acer
     * @date 11:43 2022/9/27
     * @param e 捕获错误异常
     * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Map<String,Object> bizExceptionHandler(Exception e){
        log.info("异常捕获成功：异常为:{}",e);
        Map<String,Object> map = new HashMap<>(1);
        map.put("500","异常");
        map.put("未知异常","未知问题");
        return map;
    }

    /**
     * 算数异常
     * @author acer
     * @date 11:44 2022/9/27
     * @param e 捕获错误异常
     * @return java.lang.String
    **/
    @ExceptionHandler(value = ArithmeticException.class)
    public String nullExceptionHandler(Exception e){
        log.info("异常捕获成功：异常位：{}",e);
        return "error/5xx.html";
    }





}
