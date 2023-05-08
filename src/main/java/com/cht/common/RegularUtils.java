package com.cht.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验工具类
 */
public class RegularUtils {

    // 邮箱正则
    public static final String EMAIL = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * 校验
     * @param name 正则表达式
     * @param s 判断的字符串
     * @return boolean
     */
    public static boolean check(String name,String s){

        Pattern pattern = Pattern.compile(name);

        Matcher matcher = pattern.matcher(s);

        return matcher.matches();
    }
}
