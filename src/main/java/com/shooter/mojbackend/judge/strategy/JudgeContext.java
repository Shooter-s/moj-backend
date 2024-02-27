package com.shooter.mojbackend.judge.strategy;

import com.shooter.mojbackend.model.dto.question.JudgeCase;
import com.shooter.mojbackend.model.dto.questionsubmit.JudgeInfo;
import com.shooter.mojbackend.model.po.Question;
import com.shooter.mojbackend.model.po.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * ClassName: JudgeContext
 * Package: com.shooter.mojbackend.judge.strategy
 * Description: 判题上下文，用于定义在策略中传递的参数
 *
 * @Author:Shooter
 * @Create 2024/2/27 11:31
 * @Version 1.0
 */
@Data
public class JudgeContext {

    private List<JudgeCase> judgeCases;
    private List<String> inputList;
    private List<String> outputList;
    private Question question;
    private JudgeInfo judgeInfo;
    private QuestionSubmit questionSubmit;

}
