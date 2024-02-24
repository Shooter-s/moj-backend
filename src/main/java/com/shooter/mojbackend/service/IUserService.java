package com.shooter.mojbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shooter.mojbackend.model.dto.user.UserQueryRequest;
import com.shooter.mojbackend.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shooter.mojbackend.model.vo.LoginUserVO;
import com.shooter.mojbackend.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author shooter
 * @since 2024-02-22
 */
public interface IUserService extends IService<User> {

    /**
     * 注册用户信息
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return id userName userAvatar userProfile userRole createTime updateTime
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     * @param request
     * @return 未脱敏的用户信息
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前用户信息，允许未登录
     * @param request
     * @return
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否是管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User user);

    boolean userLogout(HttpServletRequest request);

    LoginUserVO getLoginUserVO(User user);

    /**
     * 一个用户 -> 脱敏后的
     * @param user
     * @return id userName userAvatar userProfile userRole createTime
     */
    UserVO getUserVO(User user);

    /**
     * 用户列表 -> 脱敏后的用户列表
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
