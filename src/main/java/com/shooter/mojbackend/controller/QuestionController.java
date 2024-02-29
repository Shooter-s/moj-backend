package com.shooter.mojbackend.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.shooter.mojbackend.annotation.AuthCheck;
import com.shooter.mojbackend.common.Result;
import com.shooter.mojbackend.constant.UserConstant;
import com.shooter.mojbackend.enums.ResultCodeEnum;
import com.shooter.mojbackend.exception.BusinessException;
import com.shooter.mojbackend.model.dto.question.*;
import com.shooter.mojbackend.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shooter.mojbackend.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shooter.mojbackend.model.dto.user.DeleteRequest;
import com.shooter.mojbackend.model.po.Question;
import com.shooter.mojbackend.model.po.QuestionSubmit;
import com.shooter.mojbackend.model.po.User;
import com.shooter.mojbackend.model.vo.QuestionSubmitVO;
import com.shooter.mojbackend.model.vo.QuestionVO;
import com.shooter.mojbackend.service.IQuestionService;
import com.shooter.mojbackend.service.IQuestionSubmitService;
import com.shooter.mojbackend.service.IUserService;
import com.shooter.mojbackend.utils.ThrowUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 题目 前端控制器
 *
 * @author shooter
 * @since 2024-02-24
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private IQuestionService questionService;

    @Resource
    private IUserService userService;
    @Resource
    private IQuestionSubmitService questionSubmitService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param postAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Result<Long> addQuestion(@RequestBody QuestionAddRequest postAddRequest, HttpServletRequest request) {
        if (postAddRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        Question post = new Question();
        BeanUtils.copyProperties(postAddRequest, post);
        List<String> tags = postAddRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = postAddRequest.getJudgeCase();
        if (judgeCase != null){
            post.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = postAddRequest.getJudgeConfig();
        if (judgeConfig != null){
            post.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        questionService.validQuestion(post, true);
        User loginUser = userService.getLoginUser(request);
        post.setUserId(loginUser.getId());
        post.setFavourNum(0);
        post.setThumbNum(0);
        boolean result = questionService.save(post);
        ThrowUtils.throwIf(!result, ResultCodeEnum.OPERATION_ERROR);
        long newQuestionId = post.getId();
        return Result.success(newQuestionId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ResultCodeEnum.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ResultCodeEnum.NO_AUTH_ERROR);
        }
        boolean b = questionService.removeById(id);
        return Result.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param postUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest postUpdateRequest) {
        if (postUpdateRequest == null || postUpdateRequest.getId() <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        Question post = new Question();
        BeanUtils.copyProperties(postUpdateRequest, post);
        List<String> tags = postUpdateRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = postUpdateRequest.getJudgeCase();
        if (judgeCase != null){
            post.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = postUpdateRequest.getJudgeConfig();
        if (judgeConfig != null){
            post.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(post, false);
        long id = postUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ResultCodeEnum.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(post);
        return Result.success(result);
    }

    /**
     * 根据 id 获取题目信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public Result<Question> getQuestionById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ResultCodeEnum.NOT_FOUND_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        // 不是本人或管理员，不能直接获取所有信息
        if (!question.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ResultCodeEnum.NO_AUTH_ERROR);
        }
        return Result.success(question);
    }

    /**
     * 根据 id 获取(脱敏后的信息)
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public Result<QuestionVO> getQuestionVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        Question post = questionService.getById(id);
        if (post == null) {
            throw new BusinessException(ResultCodeEnum.NOT_FOUND_ERROR);
        }
        return Result.success(questionService.getQuestionVO(post, request));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public Result<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest postQueryRequest,
                                                       HttpServletRequest request) {
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ResultCodeEnum.PARAMS_ERROR);
        Page<Question> postPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(postQueryRequest));
        return Result.success(questionService.getQuestionVOPage(postPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public Result<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest postQueryRequest,
                                                         HttpServletRequest request) {
        if (postQueryRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        postQueryRequest.setUserId(loginUser.getId());
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ResultCodeEnum.PARAMS_ERROR);
        Page<Question> postPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(postQueryRequest));
        return Result.success(questionService.getQuestionVOPage(postPage, request));
    }

    // endregion


    /**
     * 编辑（用户）
     *
     * @param postEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public Result<Boolean> editQuestion(@RequestBody QuestionEditRequest postEditRequest, HttpServletRequest request) {
        if (postEditRequest == null || postEditRequest.getId() <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        Question post = new Question();
        BeanUtils.copyProperties(postEditRequest, post);
        List<String> tags = postEditRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = postEditRequest.getJudgeCase();
        if (judgeCase != null){
            post.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = postEditRequest.getJudgeConfig();
        if (judgeConfig != null){
            post.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(post, false);
        User loginUser = userService.getLoginUser(request);
        long id = postEditRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ResultCodeEnum.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ResultCodeEnum.NO_AUTH_ERROR);
        }
        boolean result = questionService.updateById(post);
        return Result.success(result);
    }

    /**
     * 分页获取题目列表（仅管理员）
     */
    @PostMapping("/list/page")
    public Result<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                     HttpServletRequest request){
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ResultCodeEnum.PARAMS_ERROR);
        Page<Question> postPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return Result.success(postPage);
    }


    @PostMapping("/question_submit/do")
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
    @PostMapping("/question_submit/list/page")
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
