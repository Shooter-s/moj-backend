package com.shooter.mojbackend.model.dto.questionsubmit;

import com.shooter.mojbackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: QuestionSubmitQueryRequest
 * Package: com.shooter.mojbackend.model.dto.questionsubmit
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/25 22:44
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言(如果不是本人虽然看不到代码，但是选择语言可以看到不同语言的执行效率)
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;


    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
