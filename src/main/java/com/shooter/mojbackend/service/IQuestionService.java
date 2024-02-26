package com.shooter.mojbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shooter.mojbackend.model.dto.question.QuestionQueryRequest;
import com.shooter.mojbackend.model.po.Ques;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shooter.mojbackend.model.vo.QuestionVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 题目 服务类
 *
 * @author shooter
 * @since 2024-02-24
 */
public interface IQuestionService extends IService<Ques> {


    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Ques question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Ques> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    
    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Ques question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Ques> questionPage, HttpServletRequest request);
    
}
