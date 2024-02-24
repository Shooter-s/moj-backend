package com.shooter.mojbackend.model.dto.user;

import com.shooter.mojbackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: UserQueryRequest
 * Package: com.shooter.mojbackend.model.dto.user
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:21
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true) // 子类继承时才可加的注解，对子类的属性和父类的属性生成equals方法和hashcode方法
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    /**
     * 用户昵称
     */
    private String userName;

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

