package com.shooter.mojbackend.judge;

import cn.hutool.json.JSONUtil;
import com.shooter.mojbackend.enums.JudgeInfoMessageEnum;
import com.shooter.mojbackend.enums.QuestionSubmitStatusEnum;
import com.shooter.mojbackend.enums.ResultCodeEnum;
import com.shooter.mojbackend.exception.BusinessException;
import com.shooter.mojbackend.judge.codesandbox.CodeSandbox;
import com.shooter.mojbackend.judge.codesandbox.CodeSandboxFactory;
import com.shooter.mojbackend.judge.codesandbox.CodeSandboxProxy;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import com.shooter.mojbackend.judge.strategy.DefaultJudgeStrategy;
import com.shooter.mojbackend.judge.strategy.JudgeContext;
import com.shooter.mojbackend.judge.strategy.JudgeManager;
import com.shooter.mojbackend.judge.strategy.JudgeStrategy;
import com.shooter.mojbackend.model.dto.question.JudgeCase;
import com.shooter.mojbackend.model.dto.question.JudgeConfig;
import com.shooter.mojbackend.model.dto.questionsubmit.JudgeInfo;
import com.shooter.mojbackend.model.po.Question;
import com.shooter.mojbackend.model.po.QuestionSubmit;
import com.shooter.mojbackend.model.vo.QuestionSubmitVO;
import com.shooter.mojbackend.service.IQuestionService;
import com.shooter.mojbackend.service.IQuestionSubmitService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: JudgeServiceImpl
 * Package: com.shooter.mojbackend.judge
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/27 9:39
 * @Version 1.0
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private IQuestionService questionService;

    @Resource
    private IQuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        //校验判题状态
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            // 不可重复提交
            throw new BusinessException(ResultCodeEnum.OPERATION_ERROR);
        }
        //更改判题（题目提交）的状态为 “判题中”，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        questionSubmitUpdate.setId(questionSubmitId);
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 补充判题参数
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputs = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        executeCodeRequest.setInputList(inputs);
        executeCodeRequest.setCode(questionSubmit.getCode());
        executeCodeRequest.setLanguage(questionSubmit.getLanguage());
        // 执行沙箱判题(伪沙箱)
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        // 根据沙箱的执行结果，设置题目的判题状态和信息 (策略模式)
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeCases(judgeCases);
        judgeContext.setInputList(inputs);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setQuestion(question);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext); // 策略模式，内部根据语言选择执行哪种判题策略
        // 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 返回给前端更新好判题状态的结果
        return questionSubmitService.getById(questionId);
    }
}
