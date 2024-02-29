package com.shooter.mojbackend.judge.strategy;

import com.shooter.mojbackend.judge.codesandbox.model.JudgeInfo;

/**
 * ClassName: JudgeStrategy
 * Package: com.shooter.mojbackend.judge.strategy
 * Description: 判题策略
 *
 * @Author:Shooter
 * @Create 2024/2/27 11:30
 * @Version 1.0
 */
public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext context);

}
