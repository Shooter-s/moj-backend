package com.shooter.mojbackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserRegisterRequest
 * Package: com.shooter.mojbackend.model.dto.user
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:48
 * @Version 1.0
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}

