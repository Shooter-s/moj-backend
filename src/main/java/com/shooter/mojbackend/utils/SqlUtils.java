package com.shooter.mojbackend.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: SqlUtils
 * Package: com.shooter.mojbackend.utils
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:27
 * @Version 1.0
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}

