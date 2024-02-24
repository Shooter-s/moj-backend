package com.shooter.mojbackend.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: UserRoleEnum
 * Package: com.shooter.mojbackend.enums
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:08
 * @Version 1.0
 */
public enum UserRoleEnum {

    USER("用户", "user"),
    ADMIN("管理员", "admin"),
    BAN("被封号", "ban");

    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(String value) {
        if (StrUtil.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) { // 先遍历所有的枚举变量，再根据value取值
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

