package com.cht.common;

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
public class ExceptionControllerAdvice {

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
