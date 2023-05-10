package com.cht.service;

import com.cht.pojo.Mail;
import org.springframework.web.multipart.MultipartFile;

/**
 * 发送邮箱接口
 */
public interface MailService {

    /**
     * 发送简单文本形式邮件
     * @param mail 邮件信息
     */
    void sendSimpleMail(Mail mail);

    /**
     * 发送携带文件的邮件
     * @param address 邮箱地址
     * @param headline 标题
     * @param body 内容
     * @param file 文件
     */
    void sendSimpleMailFile(String address, String headline, String body, MultipartFile file);
}
