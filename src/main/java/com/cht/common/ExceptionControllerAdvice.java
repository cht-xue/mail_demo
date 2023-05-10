package com.cht.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一异常处理器
 */
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * 默认异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e){
        e.printStackTrace();
        log.error("错误信息: {}",e.getMessage());
        return e.getMessage();
    }

    // 处理 @Valid 标注方法参数校验失败的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 获取异常中的绑定结果对象
        BindingResult bindingResult = e.getBindingResult();
        // 简单返回一个错误信息
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            return fieldError.getDefaultMessage();
        }
        return null;
    }

}
