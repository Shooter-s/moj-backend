package com.shooter.mojbackend.judge.codesandbox.model;

import com.shooter.mojbackend.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: ExecuteCodeResponse
 * Package: com.shooter.mojbackend.judge.codesandbox.model
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/27 8:47
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeResponse {

    private List<String> outputList;

    /**
     * 接口信息
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

}