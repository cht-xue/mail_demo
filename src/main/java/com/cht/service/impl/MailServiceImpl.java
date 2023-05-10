package com.cht.service.impl;

import com.cht.common.RegularUtils;
import com.cht.pojo.Mail;
import com.cht.service.MailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender javaMailSender; // JavaMailSender 的实例化操作，用于发送电子邮件

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendSimpleMail(Mail mail) {
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
    }

    @Override
    public void sendSimpleMailFile(String address, String headline, String body, MultipartFile file) {
        // 判断邮箱
        if (!RegularUtils.check(RegularUtils.EMAIL,address))
            throw new RuntimeException("请输入正确的邮箱地址");
        if (file == null)
            throw new RuntimeException("文件不能为 null");

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
                throw new RuntimeException("文件名不能为空");
            }

            // 将文件字节数组转化为 ByteArrayResource 对象，并添加为邮件附件
            helper.addAttachment(fileName,new ByteArrayResource(bytes));
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            log.error("发送携带文件邮件失败：",e);
            throw new RuntimeException("发送失败");
        }
    }
}
