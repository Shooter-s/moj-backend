package com.shooter.mojbackend.common;

import com.shooter.mojbackend.constant.CommonConstant;
import lombok.Data;

/**
 * ClassName: PageRequest
 * Package: com.shooter.mojbackend.common
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:22
 * @Version 1.0
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
