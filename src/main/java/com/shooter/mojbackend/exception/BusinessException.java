package com.shooter.mojbackend.exception;

import com.shooter.mojbackend.enums.ResultCodeEnum;

/**
 * ClassName: BusinessException
 * Package: com.shooter.mojbackend.exception
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:00
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, String message) {
        super(message);
        this.code = resultCodeEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}

