# mail-demo

## 简介

这是一个使用 Spring Boot 3 实现邮箱发送的 Demo，主要是通过 mail 实现发送

## 技术栈-环境

> 环境

- JDK 17
- maven 3.6.1

> 技术栈

| 技术              | 说明             |
| ----------------- | ---------------- |
| Spring Boot 3.0.6 | 集成框架         |
| Knife4j 4.1       | 用于生成接口文档 |
| validation        | 用于实现参数校验 |
| mail              | 用于发送邮箱     |

## 包结构

```
src/main
|-- java/com.cht
	|-- common -- 其他
	|-- controller -- 接口包
	|-- pojo -- 实体类
	|-- MailDemoApplication -- 启动类
|-- resources
	|-- application.yml -- 配置
	|-- application-mail.yml -- mail 配置
```

> common 包结构

```
common
|-- ExceptionControllerAdvice -- 统一异常处理器
|-- RegularUtils -- 正则校验工具类
```

> controller 包结构

```
controller
|-- MailController -- 邮箱接口
```

## 使用方法

需要修改 application-mail.yml 配置文件中的 username 和 password

默认使用的是 QQ 邮箱，可以修改成其他邮箱，修改时将端口也改成对应的端口

- username: 就是对应的邮箱账号
- password: 就需要去对应的邮箱获取授权码使用

```
spring:
  mail:
    host: smtp.qq.com # 配置 QQ 邮箱服务器地址
    port: 587 # 端口
    username: xx@qq.com # QQ 邮箱账号
    password: xx # QQ 邮箱的授权码
```

## 功能

- 发送简单文本形式邮件
- 发送携带文件形式邮件

