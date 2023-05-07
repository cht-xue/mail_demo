package com.cht.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Mail {

    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "请输入正确的邮箱地址")
    @Schema(description = "邮箱地址")
    @NotBlank(message = "邮箱不能为空")
    private String address;

    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    private String headline;

    @Schema(description = "发送内容")
    @NotBlank(message = "发送内容不能为空")
    private String body;

}
