package com.cht.timedtask;

import jakarta.mail.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * 定时任务，获取邮箱中邮件信息
 */
@Component
@Slf4j
public class EmailTime {

    // 获取配置文件中的邮件服务器的连接信息，此处使用 @Value 注解注入属性值，需要在配置文件中进行设置
    @Value("${spring.mail.host}")
    private String emailHost;
    @Value("${spring.mail.username}")
    private String emailUsername;
    @Value("${spring.mail.password}")
    private String emailPassword;

    // 使用 Spring 的定时任务支持，每 1 分钟执行一次读取邮件任务，此处使用了 @Scheduled 注解
    @Scheduled(cron="0 * * * * ?")
    public void monitorEmails() throws MessagingException, IOException {

        // 创建邮件客户端所需的属性，并设置 IMAP 协议类型
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        // 创建 JavaMail 邮件会话
        Session session = Session.getInstance(props, null);

        // 获取邮件服务器的存储对象
        Store store = session.getStore();
        store.connect(emailHost, emailUsername, emailPassword);

        // 获取收件箱文件夹，并且以只读模式打开，此处可以根据需要更改邮件文件夹名称
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);

        // 获取 inbox 中的所有邮件
        Message[] messages = inbox.getMessages();

        // 循环处理当前获取到的所有邮件
        for (Message message : messages) {
            // 邮件地址和主题可能不会存在，因此必需检查是否存在
            String emailFrom = message.getFrom() != null ? message.getFrom()[0].toString() : "NA";
            String subject = message.getSubject() != null ? message.getSubject() : "NA";

            // 解析邮件内容，这里只是把邮件内容按文本打印输出，如果您需要对邮件内容进行其他解析，请根据自己的需要进行修改
            StringBuilder content = new StringBuilder();
            Object messageContent = message.getContent();
            if (messageContent instanceof Multipart) {
                Multipart multipart = (Multipart) messageContent;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart.getContentType().startsWith("text/plain")) {
                        content.append(bodyPart.getContent().toString());
                    }
                }
            } else {
                content = new StringBuilder(messageContent.toString());
            }
            // 打印信息
            log.info("邮箱地址: {}\n 主题: {}\n 内容: {}",emailFrom,subject,content);
        }

        // 关闭收件箱和存储对象
        inbox.close(false);
        store.close();
    }


}
