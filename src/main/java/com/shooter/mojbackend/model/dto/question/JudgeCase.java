package com.shooter.mojbackend.model.dto.question;

import lombok.Data;

/**
 * ClassName: JudgeCase
 * Package: com.shooter.mojbackend.model.dto.question
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/24 16:26
 * @Version 1.0
 */
@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;

}
