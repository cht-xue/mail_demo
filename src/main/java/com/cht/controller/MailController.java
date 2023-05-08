package com.cht.controller;

import com.cht.common.RegularUtils;
import com.cht.pojo.Mail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RestController
@Tag(name = "发送邮件")
@RequestMapping("mail")
public class MailController {

    @Resource
    private JavaMailSender javaMailSender; // JavaMailSender 的实例化操作，用于发送电子邮件

    @Value("${spring.mail.username}")
    private String username;

    @PostMapping
    @Operation(summary = "发送简单文本形式邮件")
    public String sendSimpleMail(@RequestBody @Valid /* @Valid 注解表示开启校验*/ Mail mail){
        // 构造一个包含邮件内容的 SimpleMailMessage 实例
        SimpleMailMessage message = new SimpleMailMessage();

        // 设置邮件发件人
        message.setFrom(username);
        // 设置邮件收件人
        message.setTo(mail.getAddress());
        // 设置邮件主题
        message.setSubject(mail.getHeadline());
        // 设置邮件正文
        message.setText(mail.getBody());
        // 设置邮件发送时间，这里使用系统当前时间
        message.setSentDate(new Date());

        // 调用 JavaMailSender 发送邮件
        javaMailSender.send(message);
        return "发送成功";
    }

    @PostMapping("file")
    @Operation(summary = "发送携带文件的邮件")
    public String sendSimpleMailFile(@Parameter(description = "邮箱地址") @RequestParam String address,
                                     @Parameter(description = "标题") @RequestParam String headline,
                                     @Parameter(description = "发送内容") @RequestParam String body,
                                     @Parameter(description = "文件")  @RequestPart MultipartFile file){
        // 判断邮箱
        if (!RegularUtils.check(RegularUtils.EMAIL,address))
            return "请输入正确的邮箱地址";

        // 创建 MimeMessage 对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // 创建 MimeMessageHelper 对象，设置 from、to、subject、text、sentDate 属性
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(address);
            helper.setSubject(headline);
            helper.setText(body);
            helper.setSentDate(new Date());
            log.info("address:{}",address);

            // 将文件转换为字节数组
            byte[] bytes = file.getBytes();

            // 获取文件名字
            String fileName = file.getOriginalFilename(); // 获取文件名
            log.info("文件名字: {}",fileName);
            if (fileName == null){
                return "文件名不能为空";
            }

            // 将文件字节数组转化为 ByteArrayResource 对象，并添加为邮件附件
            helper.addAttachment(fileName,new ByteArrayResource(bytes));
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            log.error("发送携带文件邮件失败：",e);
            return "发送失败";
        }
        return "发送成功";
    }
}
