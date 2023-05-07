package com.cht.controller;

import com.cht.pojo.Mail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@Tag(name = "邮箱接口")
@RequestMapping("mail")
public class MailController {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    // @Valid 注解表示开启校验
    @PostMapping
    @Operation(summary = "发送简单文本形式邮箱")
    public String sendSimpleMail(@RequestBody @Valid Mail mail){
        //根据传入的参数构造一个包含邮件内容的SimpleMailMessage实例
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件发件人
        message.setFrom(username);
        //设置邮件收件人，这里从传入的Mail对象中获取邮件地址
        message.setTo(mail.getAddress());
        //设置邮件主题，这里从传入的Mail对象中获取邮件标题
        message.setSubject(mail.getHeadline());
        //设置邮件正文，这里从传入的Mail对象中获取邮件内容
        message.setText(mail.getBody());
        //设置邮件发送时间，这里使用系统当前时间
        message.setSentDate(new Date());
        //调用JavaMailSender发送邮件
        javaMailSender.send(message);
        //发送成功后返回一个响应，说明邮件已经成功发送
        return "发送成功";
    }
}
