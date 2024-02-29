package com.shooter.mojbackend.judge.strategy;

import com.shooter.mojbackend.judge.codesandbox.model.JudgeInfo;
import com.shooter.mojbackend.model.po.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * ClassName: JudgeManager
 * Package: com.shooter.mojbackend.judge.strategy
 * Description:判题管理（简化调用）
 *
 * @Author:Shooter
 * @Create 2024/2/27 12:10
 * @Version 1.0
 */
@Service
public class JudgeManager {

    /**
     *执行判题(封装了一层)
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
