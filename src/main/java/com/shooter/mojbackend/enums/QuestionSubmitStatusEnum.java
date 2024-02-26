package com.shooter.mojbackend.enums;

import cn.hutool.core.util.ObjectUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: QuestionSubmitStatusEnum
 * Package: com.shooter.mojbackend.enums
 * Description: 判题状态枚举
 *
 * @Author:Shooter
 * @Create 2024/2/25 19:09
 * @Version 1.0
 */
public enum QuestionSubmitStatusEnum {
    // 0 - 待判题、1 - 判题中、2 - 成功、3 - 失败
    WAITING("等待中", 0),
    RUNNING("判题中", 1),
    SUCCEED("成功", 2),
    FAILED("失败", 3);

    private final String text;

    private final Integer value;

    QuestionSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     * @return values集合
     */
    public static List<Integer> getValues(){
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据value获取枚举值
     * @return
     */
    public static QuestionSubmitStatusEnum getEnumByValue(Integer value){
        if (ObjectUtil.isEmpty(value)){
            return null;
        }
        for (QuestionSubmitStatusEnum anEnum : values()) {
            if (anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}
