package com.shooter.mojbackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserUpdateMyRequest
 * Package: com.shooter.mojbackend.model.dto.user
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 20:52
 * @Version 1.0
 */
@Data
public class UserUpdateMyRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}
