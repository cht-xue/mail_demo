package com.cht.controller;

import com.cht.pojo.Mail;
import com.cht.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Tag(name = "发送邮件")
@RequestMapping("mail")
public class MailController {

    @Resource
    private MailService mailService;

    @PostMapping
    @Operation(summary = "发送简单文本形式邮件")
    public String sendSimpleMail(@RequestBody @Valid /* @Valid 注解表示开启校验*/ Mail mail){
        mailService.sendSimpleMail(mail);
        return "发送成功";
    }

    @PostMapping("file")
    @Operation(summary = "发送携带文件的邮件")
    public String sendSimpleMailFile(@Parameter(description = "邮箱地址") @RequestParam String address,
                                     @Parameter(description = "标题") @RequestParam String headline,
                                     @Parameter(description = "发送内容") @RequestParam String body,
                                     @Parameter(description = "文件")  @RequestPart MultipartFile file){
       mailService.sendSimpleMailFile(address,headline,body,file);
        return "发送成功";
    }
}
