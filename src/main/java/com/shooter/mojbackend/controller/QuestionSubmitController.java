package com.shooter.mojbackend.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shooter.mojbackend.common.Result;
import com.shooter.mojbackend.enums.ResultCodeEnum;
import com.shooter.mojbackend.exception.BusinessException;
import com.shooter.mojbackend.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shooter.mojbackend.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shooter.mojbackend.model.po.QuestionSubmit;
import com.shooter.mojbackend.model.po.User;
import com.shooter.mojbackend.model.vo.QuestionSubmitVO;
import com.shooter.mojbackend.service.IQuestionSubmitService;
import com.shooter.mojbackend.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 题目提交 前端控制器
 * </p>
 *
 * @author shooter
 * @since 2024-02-24
 */
@RestController
@RequestMapping("/question-submit")
public class QuestionSubmitController {

    @Resource
    private IUserService userService;

    @Resource
    private IQuestionSubmitService questionSubmitService;

    @PostMapping("/")
    public Result<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                         HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return Result.success(questionSubmitId);
    }

    /**
     *分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）
     */
    @PostMapping("/list/page")
    public Result<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request){
        long current = questionSubmitQueryRequest.getCurrent();
        long pageSize = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionSubmitPage =  questionSubmitService.page(new Page<>(current,pageSize),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        User loginUser = userService.getLoginUser(request);
        Page<QuestionSubmitVO> questionSubmitVOPage = questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser);
        return Result.success(questionSubmitVOPage);
    }

}
