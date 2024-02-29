package com.shooter.mojbackend.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.shooter.mojbackend.judge.codesandbox.model.JudgeInfo;
import com.shooter.mojbackend.model.po.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * ClassName: QuestionSubmitVO
 * Package: com.shooter.mojbackend.model.vo
 * Description: 返回给前端的问题提交vo
 *
 * @Author:Shooter
 * @Create 2024/2/25 22:42
 * @Version 1.0
 */
@Data
public class QuestionSubmitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 脱敏后的用户提交信息
     */
    private UserVO userVO;

    /**
     * 脱敏后的问题
     */
    private QuestionVO questionVO;

    /**
     * vo -> obj
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO){
        if (questionSubmitVO == null){
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtil.copyProperties(questionSubmitVO,questionSubmit);
        JudgeInfo judgeInfo = questionSubmitVO.getJudgeInfo();
        if (judgeInfo != null){
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(questionSubmit));
        }
        return questionSubmit;
    }

    /**
     * obj -> vo
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit){
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        //把没有json和对象转换的属性直接拷贝过来
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(questionSubmit.getJudgeInfo(),JudgeInfo.class));
        return questionSubmitVO;
    }
}
