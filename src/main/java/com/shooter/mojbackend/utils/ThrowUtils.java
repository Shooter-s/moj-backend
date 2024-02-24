package com.shooter.mojbackend.utils;

import com.shooter.mojbackend.enums.ResultCodeEnum;
import com.shooter.mojbackend.exception.BusinessException;

/**
 * ClassName: ThrowUtils
 * Package: com.shooter.mojbackend.utils
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:53
 * @Version 1.0
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, ResultCodeEnum errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ResultCodeEnum errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
