package com.cht.pojo;

import com.cht.common.RegularUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Mail {

    @Pattern(regexp = RegularUtils.EMAIL, message = "请输入正确的邮箱地址")
    @Schema(description = "邮箱地址")
    @NotBlank(message = "邮箱不能为空")
    private String address;

    @Schema(description = "标题")
    private String headline;

    @Schema(description = "发送内容")
    private String body;

}
