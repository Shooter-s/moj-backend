package com.shooter.mojbackend.judge;

import com.shooter.mojbackend.model.po.QuestionSubmit;
import com.shooter.mojbackend.model.vo.QuestionSubmitVO;

/**
 * ClassName: JudgeService
 * Package: com.shooter.mojbackend.judge
 * Description: 判题服务
 *
 * @Author:Shooter
 * @Create 2024/2/27 9:38
 * @Version 1.0
 */
public interface JudgeService {

    /**
     * 题目提交的id
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);

}
