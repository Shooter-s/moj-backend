package com.shooter.mojbackend.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: QuestionSubmitAddRequest
 * Package: com.shooter.mojbackend.model.dto.questionsubmit
 * Description: 问题提交请求
 *
 * @Author:Shooter
 * @Create 2024/2/25 19:18
 * @Version 1.0
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}