package com.shooter.mojbackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserUpdateRequest
 * Package: com.shooter.mojbackend.model.dto.user
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 20:43
 * @Version 1.0
 */
@Data
public class UserUpdateRequest implements Serializable {
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
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
