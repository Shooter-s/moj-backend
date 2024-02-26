package com.shooter.mojbackend.model.dto.question;

import lombok.Data;

/**
 * ClassName: JudgeConfig
 * Package: com.shooter.mojbackend.model.dto.question
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/24 16:24
 * @Version 1.0
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 内存限制(kb)
     */
    private Long memoryLimit;

    /**
     * 堆栈限制(kb)
     */
    private Long stackLimit;

}
