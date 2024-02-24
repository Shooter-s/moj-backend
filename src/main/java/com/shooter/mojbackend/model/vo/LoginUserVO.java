package com.shooter.mojbackend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: LoginUserVO
 * Package: com.shooter.mojbackend.model.vo
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:06
 * @Version 1.0
 */
@Data
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}

