package com.cht;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
public class MailDemoApplication implements ApplicationRunner {

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(MailDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取本机 ip
        String addr = InetAddress.getLocalHost().getHostAddress();
        log.info("------------------------------------------------------------------------------------");
        log.info("Local: http://localhost:"+ port);
        log.info("swagger API 文档：http://"+ addr +":"+ port + "/doc.html");
        log.info("------------------------------------------------------------------------------------");
    }
}
