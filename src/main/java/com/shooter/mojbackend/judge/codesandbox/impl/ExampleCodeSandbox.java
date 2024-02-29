package com.shooter.mojbackend.judge.codesandbox.impl;

import com.shooter.mojbackend.enums.JudgeInfoMessageEnum;
import com.shooter.mojbackend.enums.QuestionSubmitStatusEnum;
import com.shooter.mojbackend.judge.codesandbox.CodeSandbox;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import com.shooter.mojbackend.judge.codesandbox.model.JudgeInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ClassName: ExampleCodeSandbox
 * Package: com.shooter.mojbackend.judge.codesandbox.impl
 * Description: 示例代码沙箱，跑到假代码
 *
 * @Author:Shooter
 * @Create 2024/2/27 8:55
 * @Version 1.0
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
