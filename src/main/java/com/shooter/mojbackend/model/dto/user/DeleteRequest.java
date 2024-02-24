package com.shooter.mojbackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: DeleteRequest
 * Package: com.shooter.mojbackend.model.dto.user
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 20:42
 * @Version 1.0
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}

