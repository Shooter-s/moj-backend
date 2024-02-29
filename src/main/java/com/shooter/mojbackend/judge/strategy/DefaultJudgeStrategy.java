package com.shooter.mojbackend.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.shooter.mojbackend.enums.JudgeInfoMessageEnum;
import com.shooter.mojbackend.model.dto.question.JudgeCase;
import com.shooter.mojbackend.model.dto.question.JudgeConfig;
import com.shooter.mojbackend.judge.codesandbox.model.JudgeInfo;
import com.shooter.mojbackend.model.po.Question;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: DefaultJudgeStrategy
 * Package: com.shooter.mojbackend.judge.strategy
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/27 11:31
 * @Version 1.0
 */

public class DefaultJudgeStrategy implements JudgeStrategy {
    /**
     * 执行判题
     *
     * @param context
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext context) {
        // 获取上下文参数
        List<JudgeCase> judgeCases = context.getJudgeCases();
        List<String> inputList = context.getInputList();
        List<String> outputList = context.getOutputList();
        Question question = context.getQuestion();
        JudgeInfo judgeInfo = context.getJudgeInfo();
        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        // 校验正确性
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        // 先把时间和内存装备好
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        // 1 长度不一样
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 2 逐步校验答案的正确性
        for (int i = 0; i < judgeCases.size(); i++) {
            JudgeCase judgeCase = judgeCases.get(i);
            if (!outputList.get(i).equals(judgeCase.getOutput())){
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 3 限制条件校验
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needTimeLimit = judgeConfig.getTimeLimit();
        Long needMemoryLimit = judgeConfig.getMemoryLimit();

        if (memory > needMemoryLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if(time > needTimeLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 执行到这说明通过测试
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
