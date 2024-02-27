package com.shooter.mojbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shooter.mojbackend.model.dto.question.QuestionQueryRequest;
import com.shooter.mojbackend.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shooter.mojbackend.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shooter.mojbackend.model.po.Question;
import com.shooter.mojbackend.model.po.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shooter.mojbackend.model.po.User;
import com.shooter.mojbackend.model.vo.QuestionSubmitVO;
import com.shooter.mojbackend.model.vo.QuestionVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>
 * 题目提交 服务类
 * </p>
 *
 * @author shooter
 * @since 2024-02-24
 */
public interface IQuestionSubmitService extends IService<QuestionSubmit> {


    /**
     * 执行任务提交的代码
     * @param questionSubmitAddRequest questionId code language
     * @param loginUser 提交用户
     * @return 任务提交的id
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 根据分页查询过来的条件进行脱敏
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
