package com.shooter.mojbackend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: UserVO
 * Package: com.shooter.mojbackend.model.vo
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:20
 * @Version 1.0
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
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

    private static final long serialVersionUID = 1L;
}
