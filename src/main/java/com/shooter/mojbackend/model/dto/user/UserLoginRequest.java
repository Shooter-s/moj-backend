package com.shooter.mojbackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserLoginRequest
 * Package: com.shooter.mojbackend.model.dto.user
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:50
 * @Version 1.0
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
