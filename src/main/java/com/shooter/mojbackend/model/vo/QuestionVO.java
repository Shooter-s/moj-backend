package com.shooter.mojbackend.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.shooter.mojbackend.model.dto.question.JudgeConfig;
import com.shooter.mojbackend.model.po.Ques;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName: QuestionVO
 * Package: com.shooter.mojbackend.model.vo
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/24 16:35
 * @Version 1.0
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建题目人的信息
     */
    private UserVO userVO;

    /**
     * 包装类转对象(对象存取的json数据是string类型，而vo中存的json数据为了方便前端获取内部属性，所以需要转换)
     * QuestionVO -> Question
     */
    public static Ques voToObj(QuestionVO questionVO){
        if (questionVO == null) {
            return null;
        }
        Ques question = new Ques();
        //把没有json和对象转换的属性直接拷贝过来
        BeanUtil.copyProperties(questionVO,question);
        //vo tags    List->String
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        //vo JudgeConfig  JudgeConfig -> String
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if (voJudgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     * Question -> QuestionVO
     */
    public static QuestionVO objToVo(Ques question){
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        //把没有json和对象转换的属性直接拷贝过来
        BeanUtils.copyProperties(question, questionVO);
        // string -> List<String>
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tagList);
        // string -> JudgeConfig
        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return questionVO;
    }
}
