package com.shooter.mojbackend.constant;

/**
 * ClassName: UserConstant
 * Package: com.shooter.mojbackend.constant
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:15
 * @Version 1.0
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    // endregion
}
