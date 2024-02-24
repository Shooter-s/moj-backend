package com.shooter.mojbackend.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shooter.mojbackend.annotation.AuthCheck;
import com.shooter.mojbackend.common.Result;
import com.shooter.mojbackend.constant.UserConstant;
import com.shooter.mojbackend.enums.ResultCodeEnum;
import com.shooter.mojbackend.exception.BusinessException;
import com.shooter.mojbackend.model.dto.user.*;
import com.shooter.mojbackend.model.po.User;
import com.shooter.mojbackend.model.vo.LoginUserVO;
import com.shooter.mojbackend.model.vo.UserVO;
import com.shooter.mojbackend.service.IUserService;
import com.shooter.mojbackend.utils.ThrowUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author shooter
 * @since 2024-02-22
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
public class UserController {

    @Resource
    private IUserService userService;

    //注册
    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return Result.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return Result.success(loginUserVO);
    }


    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "注销")
    public Result<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return Result.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    @Operation(summary = "获取当前登录用户")
    public Result<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return Result.success(userService.getLoginUserVO(user));
    }

    // endregion

    // region 增删改查

    /**
     * 创建用户
     *
     * @param userAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @Operation(summary = "创建用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
        if (userAddRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ResultCodeEnum.OPERATION_ERROR);
        return Result.success(user.getId());
    }

    /**
     * 删除用户
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @Operation(summary = "删除用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return Result.success(b);
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest
     * @param request id userName userAvatar userProfile userRole
     * @return
     */
    @PostMapping("/update")
    @Operation(summary = "更新用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
                                            HttpServletRequest request) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ResultCodeEnum.OPERATION_ERROR);
        return Result.success(true);
    }

    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get")
    @Operation(summary = "根据 id 获取用户（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<User> getUserById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ResultCodeEnum.NOT_FOUND_ERROR);
        return Result.success(user);
    }

    /**
     * 根据 id 获取包装类
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get/vo")
    @Operation(summary = "根据id获取包装类")
    public Result<UserVO> getUserVOById(long id, HttpServletRequest request) {
        Result<User> response = getUserById(id, request);
        User user = response.getData();
        return Result.success(userService.getUserVO(user));
    }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @Operation(summary = "分页获取用户列表(仅管理员)")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest,
                                                   HttpServletRequest request) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        return Result.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    @Operation(summary = "分页获取用户封装列表")
    public Result<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest,
                                                       HttpServletRequest request) {
        if (userQueryRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ResultCodeEnum.PARAMS_ERROR);
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        //准备一个返回UserVO的page对象
        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
        //设置Records属性
        userVOPage.setRecords(userVO);
        return Result.success(userVOPage);
    }

    // endregion

    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest
     * @param request userName userAvatar userProfile
     * @return
     */
    @PostMapping("/update/my")
    @Operation(summary = "更新个人信息")
    public Result<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest,
                                              HttpServletRequest request) {
        if (userUpdateMyRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        User user = new User();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ResultCodeEnum.OPERATION_ERROR);
        return Result.success(true);
    }
}
