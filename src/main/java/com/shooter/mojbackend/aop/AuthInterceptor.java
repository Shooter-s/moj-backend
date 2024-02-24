package com.shooter.mojbackend.aop;

import cn.hutool.core.util.StrUtil;
import com.shooter.mojbackend.annotation.AuthCheck;
import com.shooter.mojbackend.enums.ResultCodeEnum;
import com.shooter.mojbackend.enums.UserRoleEnum;
import com.shooter.mojbackend.exception.BusinessException;
import com.shooter.mojbackend.model.po.User;
import com.shooter.mojbackend.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ClassName: AuthInterceptor
 * Package: com.shooter.mojbackend.aop
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 20:16
 * @Version 1.0
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private IUserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)") // 对于使用该注解的方法生效
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        //拿到角色信息admin
        String mustRole = authCheck.mustRole();
        //拿到request
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User loginUser = userService.getLoginUser(request);
        // 必须有该权限才通过
        if (StrUtil.isNotBlank(mustRole)) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
            if (mustUserRoleEnum == null) {
                throw new BusinessException(ResultCodeEnum.NO_AUTH_ERROR);
            }
            String userRole = loginUser.getUserRole();
            // 如果被封号，直接拒绝
            if (UserRoleEnum.BAN.equals(mustUserRoleEnum)) {
                throw new BusinessException(ResultCodeEnum.NO_AUTH_ERROR);
            }
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
                if (!mustRole.equals(userRole)) {
                    throw new BusinessException(ResultCodeEnum.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行(执行原方法)
        return joinPoint.proceed();
    }

}
